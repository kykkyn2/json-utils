# Jackson Java IO Exception 난 이렇게 처리 했다.

기존 ObjectMapper 를 사용하다보면
IOException 및 IllegalStateException 을 자주 만나게 된다.

```sh
    ObjectMapper objectMapper = new ObjectMapper();
    try {
		objectMapper.readValue(str,Account.class);
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
```

그럴때 마다 Exception 처리를 하는것이 너무 불편했다..

그래서 어떻게 할까 고민 하다가 아래와 같이 utils 처리

```sh
    public static String toJson(Object obj) {
		try {
			return JACKSON.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> T fromJson(String json, Class<T> t) {
		try {
			return JACKSON.readValue(json, t);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
```



