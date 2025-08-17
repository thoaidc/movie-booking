package vn.ptit.moviebooking.booking.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.ptit.moviebooking.booking.common.SecurityUtils;
import vn.ptit.moviebooking.booking.dto.BaseUserDTO;
import vn.ptit.moviebooking.booking.exception.BaseBadRequestException;

import java.net.URI;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FeignAuthInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String targetUrl = requestTemplate.feignTarget().url();
        String requestUrl = URI.create(targetUrl).getPath() + requestTemplate.path();
        log.info("[FEIGN_REQUEST_FORWARDED] - Filtering: {}", requestUrl);

        if (SecurityUtils.checkIfAuthenticationRequired(requestUrl, new String[]{ "/api/p/**" })) {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                BaseUserDTO userDTO = (BaseUserDTO) authentication.getPrincipal();
                Integer userId = userDTO.getId();
                String username = userDTO.getUsername();

                log.info("[FEIGN_REQUEST_FORWARDED] - userId: {}, username: {}", userId, username);
                requestTemplate.header("userId", String.valueOf(userId));
                requestTemplate.header("username", username);
            } catch (Exception e) {
                log.error("[FEIGN_REQUEST_FORWARDED_ERROR] - Missing or invalid authentication: {}", e.getMessage());
                throw new BaseBadRequestException("FeignAuthInterceptor", "Khong co thong tin xac thuc");
            }
        }
    }
}
