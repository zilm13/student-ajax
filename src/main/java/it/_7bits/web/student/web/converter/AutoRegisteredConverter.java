package it._7bits.web.student.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;

/**
 * Abstract class designed for auto registering converter classes
 * Converter should extends it to be auto-registered
 * @param <S>    from type (usually String)
 * @param <T>    to type (usually Object) conversion
 */
public abstract class AutoRegisteredConverter<S, T> implements Converter<S, T> {
    @Autowired
    private ConversionService conversionService;

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @SuppressWarnings("unused") // the IoC container will call @PostConstruct methods
    @PostConstruct
    private void register() {
        if (conversionService instanceof GenericConversionService) {
            ((GenericConversionService) conversionService).addConverter(this);
        }
    }
}