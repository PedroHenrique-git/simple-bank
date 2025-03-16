package com.simplebank.bank.infra.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationUtils
{
  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> T fromJson(String payload, Class<T> target)
  {
    try
    {
      return mapper.readValue(payload, target);
    } catch (JsonProcessingException e)
    {
      return null;
    }
  }

  public static String toJson(Object payload)
  {
    try
    {
      return mapper.writeValueAsString(payload);
    } catch (JsonProcessingException e)
    {
      return null;
    }
  }
}
