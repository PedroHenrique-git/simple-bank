package com.simplebank.bank.infra.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

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
}
