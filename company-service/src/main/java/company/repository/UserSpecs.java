package company.repository;

import company.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<User> searchUsers(String searchTerm) {
        return (root, query, builder) -> {
            query.distinct(true);
            return builder.or(
                    builder.like(root.get("username"), '%' + searchTerm + '%'),
                    builder.like(root.get("name"), '%' + searchTerm + '%'),
                    builder.like(root.join("roles").as(String.class), '%' + searchTerm + '%'),
                    builder.like(root.get("surname"), '%' + searchTerm + '%'),
                    builder.like(root.get("company").get("name"), '%' + searchTerm + '%'));
        };
    }
}
