/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jelena
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public List<Company> findByNameContainingOrHeadquartersContaining(String name, String headquarters);

}
