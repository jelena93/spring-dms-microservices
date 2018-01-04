/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import process.domain.Activity;

/**
 *
 * @author jelenas
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
