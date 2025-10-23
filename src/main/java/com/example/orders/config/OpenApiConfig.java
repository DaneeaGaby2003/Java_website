@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Order Management API")
                .version("v1")
                .description("API para gestión de órdenes (perfiles, validación y errores)"));
    }
}
