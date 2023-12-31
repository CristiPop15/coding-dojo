package com.assignment.spring.config;

import com.assignment.spring.config.exception.NotFoundException;
import com.assignment.spring.config.exception.ServerErrorException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Configuration
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().series() == SERVER_ERROR) {
            throw new ServerErrorException();
        } else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
            throw new NotFoundException();
        }
    }
}
