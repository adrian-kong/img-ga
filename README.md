### Genetic Algorithm

Genetic Algorithm implementation, recreates given image rendered using LWJGL library.

### Note

Should have used a library over GPU for this, can't speed up more parallel tasks (?)

Using faster math (?), log operations are using LUT http://graphics.stanford.edu/~seander/bithacks.html#IntegerLogFloat

### Demo

<p float="left">
  <img src="./peng.png" width="250" />
  <img src="/img.png" width="250" /> 
  <img src="/img1.png" width="250" /> 
</p>

### Build

```shell
mvn clean install
```

Probably something like:

```shell
java -jar output.jar
```
