package com.ugcs.messaging.mina;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ugcs.messaging.api.MessageEvent;
import com.ugcs.messaging.api.MessageListener;
import com.ugcs.messaging.api.MessageSelector;
import com.ugcs.messaging.api.MessageSession;

public class MinaMessageSession implements MessageSession {
	private static final Logger log = LoggerFactory.getLogger(MinaMessageSession.class);
	
	private final IoSession session;
	private final Map<MessageListener, MessageSelector> listeners = 
			new ConcurrentHashMap<MessageListener, MessageSelector>();
	
	private static final MessageSelector SELECT_ALL = new MessageSelector() {
		public boolean select(Object message) {
			return true;
		}}; 
	
	public MinaMessageSession(IoSession session) {
		if (session == null)
			throw new IllegalArgumentException("session");
		
		this.session = session;
	}
	
	@Override
	public SocketAddress getLocalAddress() {
		return session == null
				? null
				: session.getLocalAddress();
	}

	@Override
	public SocketAddress getRemoteAddress() {
		return session == null
				? null
				: session.getRemoteAddress();
	}
	
	@Override
	public void addListener(MessageListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("listener");
		
		synchronized (listeners) {
			listeners.put(listener, SELECT_ALL);
		}
	}
	
	@Override
	public void addListener(MessageListener listener, MessageSelector selector) {
		if (listener == null)
			throw new IllegalArgumentException("listener");
		if (selector == null)
			throw new IllegalArgumentException("selector");

		synchronized (listeners) {
			listeners.put(listener, selector);
		}
	}
	
	@Override
	public void removeListener(MessageListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}
	
	@Override
	public void setAttribute(Object key, Object value) {
		session.setAttribute(key, value);
	}

	@Override
	public Object getAttribute(Object key) {
		return session.getAttribute(key);
	}

	@Override
	public Object getAttribute(Object key, Object defaultValue) {
		return session.getAttribute(key, defaultValue);
	}
	
	@Override
	public boolean isOpened() {
		return session.isConnected() && !session.isClosing();
	}
	
	@Override
	public void close() {
		CloseFuture closeFuture = session.close(false);
		if (closeFuture != null)
			closeFuture.awaitUninterruptibly();
	}
	
	@Override
	public void closeNonBlocking() {
		session.close(false);
	}
	
	@Override
	public void send(Object message) {
		if (message == null)
			throw new IllegalArgumentException("Can not send <null>");
		
		session.write(message);
	}
	
	protected void messageReceived(Object message) throws Exception {
		List<MessageListener> listenersCopy = new ArrayList<MessageListener>();
		synchronized (listeners) {
			for (Map.Entry<MessageListener, MessageSelector> entry : listeners.entrySet()) {
				MessageSelector selector = entry.getValue();
				if (selector == null || selector.select(message))
					listenersCopy.add(entry.getKey());
			}
		}
		if (listenersCopy.size() == 0) {
			log.warn("No listener registered for message, message skipped");
			return;
		}
		MessageEvent messageEvent = new MessageEvent(this, this, message);
		for (MessageListener listener : listenersCopy) {
			try {
				listener.messageReceived(messageEvent);
			} catch (Throwable e) {
				// continue to the next listener
				log.warn("Listener error", e);
			}
		}
	}
	
	protected void cancelAllListeners() {
		List<MessageListener> listenersCopy = new ArrayList<MessageListener>();
		synchronized (listeners) {
			for (MessageListener listener : listeners.keySet())
				listenersCopy.add(listener);
		}
		for (MessageListener listener : listenersCopy) {
			try {
				listener.cancelled();
			} catch (Throwable e) {
				// continue to the next listener
				log.warn("Listener cancellation error", e);
			}
		}
	}
}