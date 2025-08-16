//package vn.ptit.moviebooking.booking.saga;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.converters.MarshallingContext;
//import com.thoughtworks.xstream.converters.UnmarshallingContext;
//import com.thoughtworks.xstream.io.HierarchicalStreamReader;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//import com.thoughtworks.xstream.security.AnyTypePermission;
//import org.axonframework.serialization.xml.XStreamSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class AxonConfig {
//
//    @Bean
//    @Primary
//    public XStreamSerializer xStreamSerializer() {
//        XStream xStream = xStream();
//        xStream.addPermission(AnyTypePermission.ANY); // tạm thời
//        return XStreamSerializer.builder().xStream(xStream).build();
//    }
//
//    @Bean
//    public XStream xStream() {
//        XStream xStream = new XStream();
//        xStream.allowTypesByWildcard(new String[] { "java.lang.String$CaseInsensitiveComparator" });
//        xStream.registerConverter(new ComparatorConverter());
//        return xStream;
//    }
//
//    public class ComparatorConverter implements Converter {
//        @Override
//        public boolean canConvert(Class type) {
//            return type.equals(String.CASE_INSENSITIVE_ORDER.getClass());
//        }
//
//        @Override
//        public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
//            // Không cần serialize comparator, chỉ cần flag
//            writer.setValue("CASE_INSENSITIVE_ORDER");
//        }
//
//        @Override
//        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
//            return String.CASE_INSENSITIVE_ORDER;
//        }
//    }
//}
