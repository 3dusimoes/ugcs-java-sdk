package com.ugcs.ucs.proto.mapping;

import com.ugcs.ucs.proto.MessagesProto;
import com.ugcs.ucs.proto.MessagesProto.ChangeRouteVehicleProfileRequest;
import com.ugcs.ucs.proto.MessagesProto.ChangeRouteVehicleProfileResponse;
import com.ugcs.ucs.proto.MessagesProto.GetCapabilitiesRequest;
import com.ugcs.ucs.proto.MessagesProto.GetCapabilitiesResponse;
import com.ugcs.ucs.proto.MessagesProto.GetNfzListRequest;
import com.ugcs.ucs.proto.MessagesProto.GetNfzListResponse;
import com.ugcs.ucs.proto.MessagesProto.SubmitUtmOperationRequest;
import com.ugcs.ucs.proto.MessagesProto.SubmitUtmOperationResponse;
import com.ugcs.ucs.proto.codec.ProtoMessageMapping;

public class HciMessageMapping extends ProtoMessageMapping {
	public HciMessageMapping() {
		putMapping(0, MessagesProto.Error.class);
		putMapping(1, MessagesProto.Notification.class);
		
		putMapping(10, MessagesProto.GetObjectListRequest.class);
		putMapping(11, MessagesProto.GetObjectListResponse.class);
		putMapping(12, MessagesProto.GetObjectRequest.class);
		putMapping(13, MessagesProto.GetObjectResponse.class);
		putMapping(14, MessagesProto.CreateOrUpdateObjectRequest.class);
		putMapping(15, MessagesProto.CreateOrUpdateObjectResponse.class);
		putMapping(16, MessagesProto.DeleteObjectRequest.class);
		putMapping(17, MessagesProto.DeleteObjectResponse.class);
		putMapping(18, MessagesProto.UpdateObjectFieldsRequest.class);
		putMapping(19, MessagesProto.UpdateObjectFieldsResponse.class);
		putMapping(20, MessagesProto.AuthorizeHciRequest.class);
		putMapping(21, MessagesProto.AuthorizeHciResponse.class);
		putMapping(22, MessagesProto.LoginRequest.class);
		putMapping(23, MessagesProto.LoginResponse.class);
		putMapping(24, MessagesProto.LogoutRequest.class);
		putMapping(25, MessagesProto.LogoutResponse.class);
		putMapping(30, MessagesProto.GetRasterGridRequest.class);
		putMapping(31, MessagesProto.GetRasterGridResponse.class);
		putMapping(32, MessagesProto.ProcessRouteRequest.class);
		putMapping(33, MessagesProto.ProcessRouteResponse.class);
		putMapping(34, MessagesProto.GetRasterValuesRequest.class);
		putMapping(35, MessagesProto.GetRasterValuesResponse.class);
		putMapping(36, MessagesProto.GetTelemetryRequest.class);
		putMapping(37, MessagesProto.GetTelemetryResponse.class);
		putMapping(38, MessagesProto.GetKmlRouteRepresentationRequest.class);
		putMapping(39, MessagesProto.GetKmlRouteRepresentationResponse.class);
		putMapping(42, MessagesProto.SetHciLocaleRequest.class);
		putMapping(43, MessagesProto.SetHciLocaleResponse.class);
		putMapping(44, MessagesProto.ConvertRouteAltitudesRequest.class);
		putMapping(45, MessagesProto.ConvertRouteAltitudesResponse.class);
		putMapping(46, MessagesProto.GetLineOfSightRequest.class);
		putMapping(47, MessagesProto.GetLineOfSightResponse.class);
		putMapping(48, MessagesProto.GetElevationProfileRequest.class);
		putMapping(49, MessagesProto.GetElevationProfileResponse.class);
		putMapping(50, MessagesProto.GetAdsbAircraftListRequest.class);
		putMapping(51, MessagesProto.GetAdsbAircraftListResponse.class);
		putMapping(52, MessagesProto.SearchPlacesRequest.class);
		putMapping(53, MessagesProto.SearchPlacesResponse.class);
		putMapping(54, MessagesProto.ExportRouteToXmlRequest.class);
		putMapping(55, MessagesProto.ExportRouteToXmlResponse.class);
		putMapping(56, MessagesProto.ImportRouteFromXmlRequest.class);
		putMapping(57, MessagesProto.ImportRouteFromXmlResponse.class);
		putMapping(58, MessagesProto.SubscribeEventRequest.class);
		putMapping(59, MessagesProto.SubscribeEventResponse.class);
		putMapping(60, MessagesProto.UnsubscribeEventRequest.class);
		putMapping(61, MessagesProto.UnsubscribeEventResponse.class);
		putMapping(62, MessagesProto.GetVehicleLogRequest.class);
		putMapping(63, MessagesProto.GetVehicleLogResponse.class);
		putMapping(64, MessagesProto.SendCommandRequest.class);
		putMapping(65, MessagesProto.SendCommandResponse.class);
		putMapping(66, MessagesProto.UploadRouteRequest.class);
		putMapping(67, MessagesProto.UploadRouteResponse.class);
		putMapping(68, MessagesProto.GetLicenseRequest.class);
		putMapping(69, MessagesProto.GetLicenseResponse.class);
		putMapping(70, MessagesProto.ActivateLicenseRequest.class);
		putMapping(71, MessagesProto.ActivateLicenseResponse.class);
		putMapping(72, MessagesProto.ExportMissionToXmlRequest.class);
		putMapping(73, MessagesProto.ExportMissionToXmlResponse.class);
		putMapping(74, MessagesProto.ImportMissionFromXmlRequest.class);
		putMapping(75, MessagesProto.ImportMissionFromXmlResponse.class);
		putMapping(76, MessagesProto.AcquireLockRequest.class);
		putMapping(77, MessagesProto.AcquireLockResponse.class);
		putMapping(78, MessagesProto.ReleaseLockRequest.class);
		putMapping(79, MessagesProto.ReleaseLockResponse.class);
		putMapping(80, MessagesProto.CheckLockRequest.class);
		putMapping(81, MessagesProto.CheckLockResponse.class);
		putMapping(82, MessagesProto.CheckMultipleLocksRequest.class);
		putMapping(83, MessagesProto.CheckMultipleLocksResponse.class);
		putMapping(84, MessagesProto.GetMissionPreferencesRequest.class);
		putMapping(85, MessagesProto.GetMissionPreferencesResponse.class);
		putMapping(86, MessagesProto.UpdateMissionPreferencesRequest.class);
		putMapping(87, MessagesProto.UpdateMissionPreferencesResponse.class);
		putMapping(88, MessagesProto.GetVehicleLogByTimeRangeRequest.class);
		putMapping(89, MessagesProto.GetVehicleLogByTimeRangeResponse.class);
		putMapping(90, MessagesProto.ExportTelemetryToXmlRequest.class);
		putMapping(91, MessagesProto.ExportTelemetryToXmlResponse.class);
		putMapping(92, MessagesProto.ImportTelemetryFromXmlRequest.class);
		putMapping(93, MessagesProto.ImportTelemetryFromXmlResponse.class);
		putMapping(94, MessagesProto.TraceTelemetryFramesRequest.class);
		putMapping(95, MessagesProto.TraceTelemetryFramesResponse.class);
		putMapping(96, MessagesProto.DeleteTelemetryRequest.class);
		putMapping(97, MessagesProto.DeleteTelemetryResponse.class);
		putMapping(98, MessagesProto.DeactivateLicenseRequest.class);
		putMapping(99, MessagesProto.DeactivateLicenseResponse.class);
		putMapping(100, MessagesProto.CheckForUpdatesRequest.class);
		putMapping(101, MessagesProto.CheckForUpdatesResponse.class);
		putMapping(102, MessagesProto.SendStatisticsRequest.class);
		putMapping(103, MessagesProto.SendStatisticsResponse.class);
		putMapping(104, MessagesProto.DroneshareUploadRequest.class);
		putMapping(105, MessagesProto.DroneshareUploadResponse.class);
		putMapping(106, MessagesProto.GetParametersSelectionValuesRequest.class);
		putMapping(107, MessagesProto.GetParametersSelectionValuesResponse.class);
		putMapping(108, MessagesProto.GetAdsbBulbStatusRequest.class);
		putMapping(109, MessagesProto.GetAdsbBulbStatusResponse.class);
		putMapping(110, MessagesProto.CountTelemetryRequest.class);
		putMapping(111, MessagesProto.CountTelemetryResponse.class);
		putMapping(112, MessagesProto.CalculateSplineRequest.class);
		putMapping(113, MessagesProto.CalculateSplineResponse.class);
		putMapping(114, ChangeRouteVehicleProfileRequest.class);
		putMapping(115, ChangeRouteVehicleProfileResponse.class);
		putMapping(116, GetNfzListRequest.class);
		putMapping(117, GetNfzListResponse.class);
		putMapping(118, SubmitUtmOperationRequest.class);
		putMapping(119, SubmitUtmOperationResponse.class);
		putMapping(120, GetCapabilitiesRequest.class);
		putMapping(121, GetCapabilitiesResponse.class);
		
		putMapping(240, MessagesProto.GetModelListRequest.class);
		putMapping(241, MessagesProto.GetModelListResponse.class);
	}
}