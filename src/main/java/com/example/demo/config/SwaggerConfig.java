// package com.example.demo.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//                 // You need to change the port as per your server
//                 .servers(List.of(
//                         new Server().url("https://9295.408procr.amypo.ai/")
//                 ));
//         }
// }       

// package com.example.demo.config;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.servers.Server;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.List;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//                 .info(new Info()
//                         .title("Credit Card Reward Maximizer API")
//                         .version("1.0")
//                         .description("API Documentation for Reward Maximization System"))
//                 .servers(List.of(
//                         new Server().url("https://9295.408procr.amypo.ai/")
//                 ));
//     }
// }

package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

@Bean
public OpenAPI customOpenAPI() {
final String securitySchemeName = "BearerAuth";
        
return new OpenAPI()
.info(new Info()
.title("Credit Card Reward Maximizer API")
.version("1.0")
.description("API Documentation for managing user profiles, credit cards, reward rules, and generating purchase recommendations."))
.servers(List.of(
new Server().url("https://9295.408procr.amypo.ai/").description("Production Server"),
new Server().url("http://localhost:9001").description("Local Server")
         ))
                // 1. Add the "Authorize" button globally to all endpoints
.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 2. Define the security scheme type (JWT Bearer Token)
.components(new Components()
.addSecuritySchemes(securitySchemeName, new SecurityScheme()
.name(securitySchemeName)
.type(SecurityScheme.Type.HTTP)
.scheme("bearer")
.bearerFormat("JWT")));
    }
}