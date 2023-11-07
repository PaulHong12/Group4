package com.msa.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msa.member.repository.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

    //@Id
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType") // This line is optional, Hibernate usually detects the type automatically.
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name="username")
    private String username;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "role")
    @Builder.Default
    private Set<String> roles = new HashSet<>();

    @Getter
    @ManyToMany
    @JoinTable(
            name = "member_friends",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Member> friends = new HashSet<>();

    // 양방향 관계., 누구의 친구인지 알수있음.
    @Getter
    @ManyToMany(mappedBy = "friends")
    private Set<Member> friendOf = new HashSet<>();

    public UserDto toDTO() {
        return new UserDto(this.username, this.email);
    }

    // Hibernate에 의해 필요합니다.
    public Member() {

    }

    public Member(String username, String email, String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setFriends(Set<Member> friends) {
        this.friends = friends;
    }

    public void addFriend(Member friend) {
        this.friends.add(friend);
        friend.getFriendOf().add(this); // Add this member to the friend's list of 'friendOf'
    }

    public void removeFriend(Member friend) {
        this.friends.remove(friend);
        friend.getFriendOf().remove(this); // Remove this member from the friend's list of 'friendOf'
    }

    public void setFriendOf(Set<Member> friendOf) {
        this.friendOf = friendOf;
    }
}