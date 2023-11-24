package com.msa.member.repository;

import com.msa.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);

    Member findByUsername(String username);
    @Query("SELECT m FROM Member m JOIN m.friends f WHERE f = :currentUser AND m NOT IN (SELECT f2 FROM Member f2 JOIN f2.friends m2 WHERE m2 = :currentUser)")
    Set<Member> findReceivedFriendRequests(@Param("currentUser") Member currentUser);
}
