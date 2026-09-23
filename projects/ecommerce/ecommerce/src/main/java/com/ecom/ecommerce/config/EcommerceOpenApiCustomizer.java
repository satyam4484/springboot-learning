package com.ecom.ecommerce.config;

import com.ecom.ecommerce.constant.ErrorCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EcommerceOpenApiCustomizer implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openAPI) {

        Components components = openAPI.getComponents();

        addErrorCodeSchema(components);
        addApiResponseSchema(components);
        addCommonResponses(components);

        if (openAPI.getPaths() == null) {
            return;
        }

        openAPI.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation ->
                        addResponses(operation)
                )
        );
    }

    private void addErrorCodeSchema(Components components) {

        Schema<String> schema = new StringSchema();

        schema.setEnum(
                Arrays.stream(ErrorCode.values())
                        .map(Enum::name)
                        .toList()
        );

        components.addSchemas("ErrorCode", schema);
    }

    private void addApiResponseSchema(Components components) {

        Schema<Object> schema = new ObjectSchema();

        schema.addProperty(
                "success",
                new BooleanSchema()
        );

        schema.addProperty(
                "message",
                new StringSchema()
        );

        schema.addProperty(
                "errorCode",
                new Schema<>().$ref(
                        "#/components/schemas/ErrorCode"
                )
        );

        schema.addProperty(
                "path",
                new StringSchema()
        );

        schema.addProperty(
                "data",
                new Schema<>()
        );

        schema.addProperty(
                "timestamp",
                new StringSchema()
                        .format("date-time")
        );

        components.addSchemas(
                "ApiResponse",
                schema
        );
    }

    private void addCommonResponses(Components components) {

        components.addResponses(
                "BadRequest",
                createResponse("Invalid request")
        );

        components.addResponses(
                "Unauthorized",
                createResponse("Authentication required or invalid credentials")
        );

        components.addResponses(
                "Forbidden",
                createResponse("Access denied")
        );

        components.addResponses(
                "NotFound",
                createResponse("Requested resource not found")
        );

        components.addResponses(
                "Conflict",
                createResponse("Resource conflict")
        );

        components.addResponses(
                "InternalServerError",
                createResponse("Internal server error")
        );
    }

    private ApiResponse createResponse(String description) {

        return new ApiResponse()
                .description(description)
                .content(
                        new Content()
                                .addMediaType(
                                        "application/json",
                                        new io.swagger.v3.oas.models.media.MediaType()
                                                .schema(
                                                        new Schema<>()
                                                                .$ref(
                                                                        "#/components/schemas/ApiResponse"
                                                                )
                                                )
                                )
                );
    }

    private void addResponses(Operation operation) {

        if (operation.getResponses() == null) {
            return;
        }

        if (!operation.getResponses().containsKey("400")) {
            operation.getResponses().addApiResponse(
                    "400",
                    new ApiResponse()
                            .$ref("#/components/responses/BadRequest")
            );
        }

        if (!operation.getResponses().containsKey("401")) {
            operation.getResponses().addApiResponse(
                    "401",
                    new ApiResponse()
                            .$ref("#/components/responses/Unauthorized")
            );
        }

        if (!operation.getResponses().containsKey("403")) {
            operation.getResponses().addApiResponse(
                    "403",
                    new ApiResponse()
                            .$ref("#/components/responses/Forbidden")
            );
        }

        if (!operation.getResponses().containsKey("404")) {
            operation.getResponses().addApiResponse(
                    "404",
                    new ApiResponse()
                            .$ref("#/components/responses/NotFound")
            );
        }

        if (!operation.getResponses().containsKey("409")) {
            operation.getResponses().addApiResponse(
                    "409",
                    new ApiResponse()
                            .$ref("#/components/responses/Conflict")
            );
        }

        if (!operation.getResponses().containsKey("500")) {
            operation.getResponses().addApiResponse(
                    "500",
                    new ApiResponse()
                            .$ref("#/components/responses/InternalServerError")
            );
        }
    }
}