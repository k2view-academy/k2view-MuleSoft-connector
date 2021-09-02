package com.mulesoft.connectors.fabricrestapi.internal.operation;

import static com.mulesoft.connectivity.rest.commons.internal.RestConstants.CONNECTOR_OVERRIDES;
import static com.mulesoft.connectivity.rest.commons.internal.RestConstants.REQUEST_PARAMETERS_GROUP_NAME;
import static java.util.regex.Pattern.compile;

import com.mulesoft.connectivity.rest.commons.api.configuration.RestConfiguration;
import com.mulesoft.connectivity.rest.commons.api.connection.RestConnection;
import com.mulesoft.connectivity.rest.commons.api.error.RequestErrorTypeProvider;
import com.mulesoft.connectivity.rest.commons.api.operation.BaseRestOperation;
import com.mulesoft.connectivity.rest.commons.api.operation.ConfigurationOverrides;
import com.mulesoft.connectivity.rest.commons.api.operation.HttpResponseAttributes;
import com.mulesoft.connectivity.rest.commons.api.operation.NonEntityRequestParameters;
import com.mulesoft.connectivity.rest.commons.internal.util.RestRequestBuilder;
import com.mulesoft.connectors.fabricrestapi.internal.metadata.DeleteLuLuNameIidTableOutputMetadataResolver;
import java.io.InputStream;
import java.util.regex.Pattern;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.mule.runtime.extension.api.runtime.streaming.StreamingHelper;
import org.mule.runtime.http.api.HttpConstants;

public class DeleteLuLuNameIidTableOperation extends BaseRestOperation {
  private static final Pattern LU_NAME_URI_PARAM_PATTERN = compile("\\{luName}");

  private static final Pattern IID_URI_PARAM_PATTERN = compile("\\{iid}");

  private static final Pattern TABLE_URI_PARAM_PATTERN = compile("\\{table}");

  private static final RestRequestBuilder.QueryParamFormat QUERY_PARAM_FORMAT =
      RestRequestBuilder.QueryParamFormat.MULTIMAP;

  public DeleteLuLuNameIidTableOperation() {}

  /**
   * Delete lu by lu name iid table
   *
   * <p>This operation makes an HTTP DELETE request to the /lu/{luName}/{iid}/{table} endpoint
   *
   * @param config the configuration to use
   * @param connection the connection to use
   * @param luNameUriParam LU name
   * @param iidUriParam iid
   * @param tableUriParam Table name
   * @param whereQueryParam Where String
   * @param setQueryParam set session scope
   * @param parameters the {@link NonEntityRequestParameters}
   * @param overrides the {@link ConfigurationOverrides}
   * @param streamingHelper the {@link StreamingHelper}
   * @param callback the operation's {@link CompletionCallback}
   */
  @Throws(RequestErrorTypeProvider.class)
  @DisplayName("Delete lu by lu name iid table")
  @MediaType("application/json")
  @OutputResolver(output = DeleteLuLuNameIidTableOutputMetadataResolver.class)
  public void deleteLuLuNameIidTable(
      @Config RestConfiguration config,
      @Connection RestConnection connection,
      @DisplayName("Lu Name") @Summary("LU name") String luNameUriParam,
      @DisplayName("iid") @Summary("iid") String iidUriParam,
      @DisplayName("table") @Summary("Table name") String tableUriParam,
      @DisplayName("where") @Summary("Where String") String whereQueryParam,
      @Optional @DisplayName("set") @Summary("set session scope") String setQueryParam,
      @ParameterGroup(name = REQUEST_PARAMETERS_GROUP_NAME) NonEntityRequestParameters parameters,
      @ParameterGroup(name = CONNECTOR_OVERRIDES) ConfigurationOverrides overrides,
      StreamingHelper streamingHelper,
      CompletionCallback<InputStream, HttpResponseAttributes> callback) {
    String requestPath = "/lu/{luName}/{iid}/{table}";
    requestPath = LU_NAME_URI_PARAM_PATTERN.matcher(requestPath).replaceAll(luNameUriParam);
    requestPath = IID_URI_PARAM_PATTERN.matcher(requestPath).replaceAll(iidUriParam);
    requestPath = TABLE_URI_PARAM_PATTERN.matcher(requestPath).replaceAll(tableUriParam);
    RestRequestBuilder builder =
        new RestRequestBuilder(
                connection.getBaseUri(), requestPath, HttpConstants.Method.DELETE, parameters)
            .setQueryParamFormat(QUERY_PARAM_FORMAT)
            .addHeader("accept", "application/json")
            .addQueryParam("where", whereQueryParam)
            .addQueryParam("set", setQueryParam != null ? String.valueOf(setQueryParam) : null);
    doRequest(
        config,
        connection,
        builder,
        overrides.getResponseTimeoutAsMillis(),
        streamingHelper,
        callback);
  }
}
