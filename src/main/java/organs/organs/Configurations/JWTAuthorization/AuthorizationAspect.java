package organs.organs.Configurations.JWTAuthorization;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import organs.organs.Services.Authentication.LoginService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Aspect
@Component
public class AuthorizationAspect {

    @Before("@annotation(authorization)")
    public void checkAuthorization(Authorization authorization) {

        HttpServletRequest request = getRequest();
        List<String> requiredRoles = List.of(authorization.requiredRoles());

        try {

            if (LoginService.isAuthorized(request, requiredRoles)) {

                throw new AccessDeniedException("You Dont Have Rights!");
            }
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    private HttpServletRequest getRequest() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {

            return attributes.getRequest();
        }
        else {

            throw new IllegalStateException("No HttpServletRequest found");
        }
    }
}