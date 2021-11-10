package net.mapoint.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SwaggerDefinition(
    info = @Info(
        title = "Mapoint service info Swagger Operations",
        version = "V1",
        description = "Exposes APIs for Authorization"
    ),
    produces = "application/json"
)
@Api(value = "Authorization API", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Get server info")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 500, message = "Internal error")
    })
    public String info() {
        return "{\"server\":\"client\"}";
    }

}
