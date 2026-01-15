package com.ecommerce.api.domain.ports.in;
}
    String getUsernameFromToken(String token);
     */
     * Obter username do token
    /**

    boolean validateToken(String token);
     */
     * Validar token JWT
    /**

    String login(String username, String password);
     */
     * Realizar login
    /**

    User register(String username, String email, String password, String fullName);
     */
     * Registrar novo usuário
    /**

public interface AuthUseCase {
 */
 * Port IN - Casos de uso de Autenticação
/**

import com.ecommerce.api.domain.entities.User;


