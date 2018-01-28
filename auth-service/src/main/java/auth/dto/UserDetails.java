package auth.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final String username;
    private final String password;
    private final Long companyId;
    private final boolean activated;
    private final List<GrantedAuthority> authorities;

    public UserDetails(String username, String password, Long companyId, boolean activated, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.companyId = companyId;
        this.activated = activated;
        this.authorities = authorities;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activated;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return activated;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    @Override
    public boolean isAccountNonExpired() {
        return activated;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", companyId=" + companyId +
                ", activated=" + activated +
                ", authorities=" + authorities +
                '}';
    }
}
