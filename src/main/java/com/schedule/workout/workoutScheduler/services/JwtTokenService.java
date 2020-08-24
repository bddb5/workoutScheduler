package com.schedule.workout.workoutScheduler.services;

import com.schedule.workout.workoutScheduler.database.model.UserDB;
import com.schedule.workout.workoutScheduler.exceptions.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.schedule.workout.workoutScheduler.controller.jwtToken.JWTRequest;
import com.schedule.workout.workoutScheduler.database.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    //key
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //expiration time
    Instant now = Instant.now();
    Instant expirationTime = now.plus(1,ChronoUnit.MINUTES);

    @Autowired
    IUsersRepository usersRepository;

    //validate user login and create new jwt
    public String createJwtToken(JWTRequest jwtRequest){

        UserDB userByEmail = usersRepository.findUsersByEmailAndPassword(jwtRequest.getEmail(),jwtRequest.getPassword());
        if(jwtRequest.getEmail() == null || jwtRequest.getPassword() == null || userByEmail == null){
            throw new UnauthorizedException();
        }
            String subject = userByEmail.getId();
            return "JWT : " + Jwts.builder().setSubject(subject).setIssuedAt(Date.from(now)).
                    setExpiration(Date.from(expirationTime)).signWith(key).compact();

    }
    //get user id from token
    public String getUserFromToken(String jwtToken) throws UnauthorizedException {

        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return "UserID : " + claims.getBody().getSubject();

    }
}




