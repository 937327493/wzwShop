package com.wzw.account.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static long tokenExpiration = 1000 * 60 * 60 * 24;
    //    private static long tokenExpiration = 1000 * 60;
    private static String tokenSignKey = "a1d23mi789n";

    public static String createToken(Integer id,String mobile){
        String token = Jwts.builder()
                //载荷：自定义信息
                .claim("id", id)
                .claim("mobile", mobile)
                //载荷：默认信息
                .setSubject("uushop-user") //令牌主题
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpiration)) //过期时间
                .setId(UUID.randomUUID().toString())
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)
                //组装jwt字符串
                .compact();
        return token;
    }

    public static boolean checkToken(String token){
        if(StringUtils.isEmpty(token)){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        String token = JwtUtil.createToken(26, "13998786612");
//        System.out.println(token);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjYsIm1vYmlsZSI6IjEzOTk4Nzg2NjEyIiwic3ViIjoidXVzaG9wLXVzZXIiLCJleHAiOjE2MzM3ODQ4MDksImp0aSI6IjIwNTY2ZjlhLTU3MDMtNDc5My1hNjRmLTRkOGJmMjcyMjRjYSJ9.J-4qI1CACtF0g_zEZ22D7IiahIGX4Z3qgpxn0vtdTUM";
        boolean b = JwtUtil.checkToken(token);
        System.out.println(b);
    }

}
