package com.am.ipl.analytics.repository;

import com.am.ipl.analytics.entity.CricketMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<CricketMatch, String> {

}
