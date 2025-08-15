package vn.ptit.moviebooking.movie.saga;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        // Cho phép các class của bạn
        xStream.addPermission(AnyTypePermission.ANY); // tạm thời, nhanh nhưng không an toàn
        // Cách an toàn hơn:
        // xStream.addPermission(new ExplicitTypePermission(
        //      Arrays.asList(
        //          vn.ptit.moviebooking.common.Command.CreateBookingCommand.class,
        //          vn.ptit.moviebooking.common.Command.ReserveSeatCommand.class,
        //          vn.ptit.moviebooking.common.Event.SeatReservedEvent.class,
        //          ...
        //      )
        // ));
        return xStream;
    }

    @Bean
    public XStreamSerializer xStreamSerializer(XStream xStream) {
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}
