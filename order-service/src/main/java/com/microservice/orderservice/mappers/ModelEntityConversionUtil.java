package com.microservice.orderservice.mappers;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
@Service
public class ModelEntityConversionUtil {
    public  <S, T> T convert(S obj, Class<T> type) {
        if(null == obj) {
            return null;
        }

        ModelMapper mapper = new ModelMapper();
        return mapper.map(obj, type);
    }

    public  <S, T> List<T> convertList(List<S> objs, Class<T> type) {
        if(null == objs || objs.isEmpty()) {
            return null;
        }

        ModelMapper mapper = new ModelMapper();
        List<T> result = new ArrayList<>();
        objs.forEach(obj -> {
            result.add(mapper.map(obj, type));
        });
        return result;
    }


    public static <S, T> Page<T> convertPage(Page<S> objects, Class<T> type) {
        if(null == objects) {
            return null;
        }

        ModelMapper modelMapper = new ModelMapper();
        Page<T> result = objects.map(new Function<S, T>() {

            @Override
            public T apply(S obj){
                if(null!=obj){
                    return modelMapper.map(obj,type);
                }
                return null;
            }
        });
        return result;
    }

}
