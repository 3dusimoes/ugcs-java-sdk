package com.ugcs.ucs.client;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.google.protobuf.Message;
import com.ugcs.ucs.proto.DomainProto.Command;
import com.ugcs.ucs.proto.DomainProto.DomainObjectWrapper;
import com.ugcs.ucs.proto.DomainProto.EventSubscriptionWrapper;
import com.ugcs.ucs.proto.DomainProto.LocalisedMessage;
import com.ugcs.ucs.proto.DomainProto.Route;
import com.ugcs.ucs.proto.DomainProto.RouteProcessingStatus;
import com.ugcs.ucs.proto.DomainProto.RouteSegment;
import com.ugcs.ucs.proto.DomainProto.TelemetrySubscription;
import com.ugcs.ucs.proto.DomainProto.Vehicle;
import com.ugcs.ucs.proto.MessagesProto.AcquireLockRequest;
import com.ugcs.ucs.proto.MessagesProto.AuthorizeHciRequest;
import com.ugcs.ucs.proto.MessagesProto.AuthorizeHciResponse;
import com.ugcs.ucs.proto.MessagesProto.GetObjectListRequest;
import com.ugcs.ucs.proto.MessagesProto.GetObjectListResponse;
import com.ugcs.ucs.proto.MessagesProto.LoginRequest;
import com.ugcs.ucs.proto.MessagesProto.LogoutRequest;
import com.ugcs.ucs.proto.MessagesProto.ProcessRouteRequest;
import com.ugcs.ucs.proto.MessagesProto.ProcessRouteResponse;
import com.ugcs.ucs.proto.MessagesProto.ReleaseLockRequest;
import com.ugcs.ucs.proto.MessagesProto.SendCommandRequest;
import com.ugcs.ucs.proto.MessagesProto.SubscribeEventRequest;
import com.ugcs.ucs.proto.MessagesProto.SubscribeEventResponse;
import com.ugcs.ucs.proto.MessagesProto.UnsubscribeEventRequest;
import com.ugcs.ucs.proto.MessagesProto.UploadRouteRequest;

public class ClientSession {
	protected final Client client;
	protected int clientId = -1;

	public ClientSession(Client client) {
		Objects.requireNonNull(client);

		this.client = client;
	}

	/* authorization and authentication */

	public void authorizeHci() throws Exception {
		authorizeHci(null);
	}

	public void authorizeHci(Locale locale) throws Exception {
		clientId = -1;
		AuthorizeHciRequest.Builder builder = AuthorizeHciRequest.newBuilder()
				.setClientId(clientId);
		if (locale != null)
			builder.setLocale(locale.toLanguageTag());
		AuthorizeHciRequest request = builder.build();
		AuthorizeHciResponse response = client.execute(request);
		clientId = response.getClientId();
	}

	public void login(String login, String password) throws Exception {
		Objects.requireNonNull(login);
		Objects.requireNonNull(password);

		LoginRequest request = LoginRequest.newBuilder()
				.setClientId(clientId)
				.setUserLogin(login)
				.setUserPassword(password)
				.build();
		client.execute(request);
	}

	public void logout() throws Exception {
		LogoutRequest request = LogoutRequest.newBuilder()
				.build();
		client.execute(request);
	}

	public List<DomainObjectWrapper> getObjectList(Class<? extends Message> objectType) throws Exception {
		return getObjectList(objectType, false);
	}

	public List<DomainObjectWrapper> getObjectList(Class<? extends Message> objectType, boolean refreshDependencies)
			throws Exception {
		GetObjectListRequest request = GetObjectListRequest.newBuilder()
				.setClientId(clientId)
				.setObjectType(objectType.getSimpleName())
				.setRefreshDependencies(refreshDependencies)
				.build();
		GetObjectListResponse response = client.execute(request);
		return response.getObjectsList();
	}

	public void acquireLock(Class<? extends Message> objectType, int objectId) throws Exception {
		Objects.requireNonNull(objectType);

		AcquireLockRequest request = AcquireLockRequest.newBuilder()
				.setClientId(clientId)
				.setObjectType(objectType.getSimpleName())
				.setObjectId(objectId)
				.build();
		client.execute(request);
	}

	public void releaseLock(Class<? extends Message> objectType, int objectId) throws Exception {
		Objects.requireNonNull(objectType);

		ReleaseLockRequest request = ReleaseLockRequest.newBuilder()
				.setClientId(clientId)
				.setObjectType(objectType.getSimpleName())
				.setObjectId(objectId)
				.setIfExclusive(true)
				.build();
		client.execute(request);
	}

	/* route processing */

	public Route processRoute(Route route) throws Exception {
		Objects.requireNonNull(route);

		ProcessRouteRequest request = ProcessRouteRequest.newBuilder()
				.setClientId(clientId)
				.setRoute(route)
				.build();
		ProcessRouteResponse response = client.execute(request);
		Route processedRoute = response.getRoute();
		boolean processed = true;
		StringBuilder errorMessage = new StringBuilder();
		for (RouteSegment segment : processedRoute.getSegmentsList()) {
			if (segment.getRouteStatus() != RouteProcessingStatus.RPS_PROCESSED) {
				processed = false;
				for (LocalisedMessage message : segment.getMessageSet().getMessagesList()) {
					errorMessage.append(message.getSeverity())
						.append(": ")
						.append(message.getDefaultText())
						.append("\n");
				}
			}
		}
		if (!processed)
			throw new IllegalStateException("Route processing error: " + errorMessage.toString());
		return processedRoute;
	}

	public void uploadRoute(Route route, Vehicle vehicle) throws Exception {
		Objects.requireNonNull(route);
		Objects.requireNonNull(vehicle);

		UploadRouteRequest request = UploadRouteRequest.newBuilder()
				.setClientId(clientId)
				.setRoute(route)
				.setVehicle(vehicle)
				.build();
		client.execute(request);
	}

	/* commands */

	public void sendCommand(Vehicle vehicle, Command command) throws Exception {
		Objects.requireNonNull(vehicle);

		SendCommandRequest request = SendCommandRequest.newBuilder()
				.setClientId(clientId)
				.setVehicle(vehicle)
				.setCommand(command)
				.build();
		client.execute(request);
	}

	/* vehicle-specific shorthands */

	public void gainVehicleControl(Vehicle vehicle) throws Exception {
		Objects.requireNonNull(vehicle);
		acquireLock(Vehicle.class, vehicle.getId());
	}

	public void releaseVehicleControl(Vehicle vehicle) throws Exception {
		Objects.requireNonNull(vehicle);
		releaseLock(Vehicle.class, vehicle.getId());
	}

	public Vehicle lookupVehicle(String vehicleName) throws Exception {
		if (vehicleName == null || vehicleName.isEmpty())
			throw new IllegalArgumentException("vehicleName cannot be empty");

		List<DomainObjectWrapper> vehicles = getObjectList(Vehicle.class);
		for (DomainObjectWrapper w : vehicles) {
			if (w != null
					&& w.getVehicle() != null
					&& vehicleName.equalsIgnoreCase(w.getVehicle().getName()))
				return w.getVehicle();
		}
		return null;
	}

	/* event subscriptions */

	public int subscribeTelemetryEvent() throws Exception {
		SubscribeEventRequest request = SubscribeEventRequest.newBuilder()
				.setClientId(clientId)
				.setSubscription(EventSubscriptionWrapper.newBuilder()
						.setTelemetrySubscription(TelemetrySubscription.newBuilder()))
				.build();
		SubscribeEventResponse response = client.execute(request);
		return response.getSubscriptionId();
	}

	public void unsubscribe(int subscriptionId) throws Exception {
		UnsubscribeEventRequest request = UnsubscribeEventRequest.newBuilder()
				.setClientId(clientId)
				.setSubscriptionId(subscriptionId)
				.build();
		client.execute(request);
	}
}