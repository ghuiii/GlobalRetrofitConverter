# GlobalRetrofitConverter
[ ![Download](https://api.bintray.com/packages/ghui/Java/global-retrofit-converter/images/download.svg) ](https://bintray.com/ghui/Java/global-retrofit-converter/_latestVersion)

If you have more than one datasource type, such as json, xml, protobuf, or even html. 
You may need more than one converter.
This is library is inspired from [a JakeWharton's presentation](https://speakerdeck.com/jakewharton/making-retrofit-work-for-you-ohio-devfest-2016).
And the source is very simple, I make this library just for later Convenience.

# Example & Usage
Let me suppose that you have have two kinds of data type, json and html.
And Below is your retrofit service. You just need add an annotation to the method. 

```java
interface Service {
    @Html
    @GET("/html") Call<FruitInfo> fruitInfoFromHtml();

    @Json
    @GET("/json") Call<PersonInfo> personInfoFromJson();
}
```

Then 

```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GlobalConverterFactory.create()
                        .add(GsonConverterFactory.create(), Json.class) 
                        .add(FruitConverterFactory.create(), Html.class))
                .build();
        service = retrofit.create(Service.class);
```

If you need other converter, you can create the annotation by yourself. For example you need a `xml` converter.

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Xml {
}
```
Then add it to the GlobalConverterFactory.

# Who Use?
* [V2er - A Nice V2EX App.](https://play.google.com/store/apps/details?id=me.ghui.v2er.free)

# Installation

* Gradle

```groovy
compile 'me.ghui:global-retrofit-converter:lastest.version'
```

* Maven

```groovy
<dependency>
  <groupId>me.ghui</groupId>
  <artifactId>global-retrofit-converter</artifactId>
  <version>lastest.version</version>
  <type>pom</type>
</dependency>
```

# License
Copyright 2017 ghui

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

