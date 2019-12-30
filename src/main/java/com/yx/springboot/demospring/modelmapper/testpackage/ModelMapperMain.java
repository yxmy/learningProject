package com.yx.springboot.demospring.modelmapper.testpackage;

import com.yx.springboot.demospring.modelmapper.dto.OrderDTO;
import com.yx.springboot.demospring.modelmapper.model.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;

public class ModelMapperMain {

    public static void main(String[] args) {
        Name name = new Name("鑫", "袁");
        Customer customer = new Customer();
        customer.setName(name);
        Address address = new Address("畅悦居", "北京市");
        SourceModel model = new SourceModel();
        model.setAddress(address);
        model.setCustomer(customer);
        model.setMark("aaaa");

        ModelMapper mapper = new ModelMapper();

//        PropertyMap<SourceModel, OrderDTO> modelOrderDtoPropertyMap = new PropertyMap<SourceModel, OrderDTO>() {
//            @Override
//            protected void configure() {
//                map(source.getCustomer().getName().getFirstName(), destination.getFirstName());
//                //跳过设置某属性
//                skip(source.getMark(), destination.getMark());
//            }
//        };
//        mapper.addMappings(modelOrderDtoPropertyMap);

        //转换器
        Converter<String, String> toUppercase =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().toUpperCase();
        Provider<Person> personProvider = req -> new Person();

        TypeMap<SourceModel, OrderDTO> typeMap = mapper.createTypeMap(SourceModel.class, OrderDTO.class);
        typeMap.addMappings(mapper0 -> mapper0.with(personProvider).map(SourceModel::getPerson, OrderDTO::setPerson));
        typeMap.addMapping(source -> source.getCustomer().getName().getLastName(), OrderDTO::setFirstName);
        typeMap.addMappings(mapper1 -> mapper1.map(SourceModel::getMark, OrderDTO::setMark));
        typeMap.addMappings(mapping2 -> mapping2.using(toUppercase).map(SourceModel::getMark, OrderDTO::setMark));
        typeMap.addMappings(mapping1 -> mapping1.map(src -> src.getCustomer().getName().getFirstName(), OrderDTO::setCustomerLastName));
        typeMap.includeBase(SourceModel.class, OrderDTO.class);
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        OrderDTO dto = mapper.map(model, OrderDTO.class);
        mapper.validate();
        System.out.println(dto);
    }


}
