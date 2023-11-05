package com.hegde.todo.domain;

public class UserPrincipal{// implements UserDetails, OAuth2User {

//    /** Serial */
//    private static final long serialVersionUID = 1793158312634815118L;
//
//    private long id;
//    private String email;
//    private String password;
//    private int countryId;
//    private Collection<? extends GrantedAuthority> authorities;
//    private Map<String, Object> attributes;
//
//    public UserPrincipal(long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
//    }
//
//    @Override
//    public String getName() {
//        return email;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
