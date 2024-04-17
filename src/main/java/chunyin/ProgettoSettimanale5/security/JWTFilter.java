package chunyin.ProgettoSettimanale5.security;

import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.exceptions.UnauthorizedException;
import chunyin.ProgettoSettimanale5.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserisci il token nell'Authorization Header");
        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        filterChain.doFilter(request, response);
        String id = jwtTools.extractIdFromToken(accessToken);
        Employee currentEmployee = this.employeeService.findById(UUID.fromString(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, currentEmployee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
