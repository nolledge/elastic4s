package com.sksamuel.elastic4s.mappings

import com.sksamuel.elastic4s.ElasticDsl
import com.sksamuel.elastic4s.mappings.dynamictemplate.DynamicMapping
import com.sksamuel.elastic4s.testkit.DiscoveryLocalNodeProvider
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar

class MappingApiTest extends FlatSpec with MockitoSugar with DiscoveryLocalNodeProvider with ElasticDsl {

  "a put mapping dsl" should "be accepted by the client" in {
    client.execute {
      putMapping("index" / "type").as(
        geopointField("name"),
        dateField("content") nullValue "no content"
      )
    }
  }

  it should "accept same fields as mapping api" in {
    putMapping("index" / "type").as(
      dateField("content") nullValue "no content"
    ) dynamic DynamicMapping.False numericDetection true boostNullValue 12.2 boostName "boosty"
  }

  "the get mapping dsl" should "be accepted by the client" in {
    client.execute {
      getMapping("index").types("type")
    }
  }

  it should "support multiple indexes" in {
    client.execute {
      getMapping("index1", "index2")
    }
  }

  it should "support multiple types" in {
    client.execute {
      getMapping("index").types("type1", "type2")
    }
  }

  it should "support multiple indexes and multiple types" in {
    client.execute {
      getMapping("index1", "index2").types("type1", "type2")
    }
  }
}