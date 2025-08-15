package vn.ptit.moviebooking.booking.saga;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    @Primary
    public XStreamSerializer xStreamSerializer() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY); // tạm thời
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}
