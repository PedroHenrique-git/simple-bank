package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractSpringController
{
  protected final ObjectMapper mapper;
  protected final ObjectNode body;

  protected AbstractSpringController()
  {
    this.mapper = new ObjectMapper();
    this.body = mapper.createObjectNode();
  }

  protected <T> void setBodyData(List<String> errors, T body)
  {
    setError(errors);
    setData(body);
  }

  private void setError(List<String> errors)
  {
    if (errors.isEmpty())
    {
      this.body.remove("errors");

      return;
    }

    this.body.set("errors", mapper.valueToTree(errors));
  }

  private <T> void setData(T body)
  {
    if (body == null)
    {
      this.body.remove("data");

      return;
    }

    this.body.set("data", mapper.valueToTree(body));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handler(Exception exception)
  {
    Map<String, String> body = new HashMap<>();

    body.put("message", exception.getMessage());

    return ResponseEntity.badRequest().body(body);
  }
}
