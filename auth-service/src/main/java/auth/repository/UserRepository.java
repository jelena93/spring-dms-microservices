/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.repository;

import auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jelena
 */
public interface UserRepository extends JpaRepository<User, String>{
    
}
