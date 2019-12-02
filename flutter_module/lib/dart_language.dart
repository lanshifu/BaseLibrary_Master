class DartTest1 {


  void printInteger(var a) {
    print("print = $a");
  }


  void listMap(){
    var arr1 = ["Tom", "Andy", "Jack"];
    var arr2 = List.of([1,2,3]);
    arr2.add(499);
    arr2.forEach((v) => print('${v}'));

    var map1 = {"name": "Tom", 'sex': 'male'};
    var map2 = new Map();
    map2['name'] = 'Tom';
    map2['sex'] = 'male';
    map2.forEach((k,v) => print('${k}: ${v}'));

  }


  void main() {
    var year = 2019;
    printInteger(year);

    num n = 10 + 20.3;
    bool isLogin = false;
    String text = "ok";

    if (isLogin) {
      assert(n == 0);
    }
    if (n == 0) {}

    printInteger(n);
    printInteger(isLogin);
    printInteger(text);

    var s = 'cat';
    var s1 = 'this is a uppercased string: ${s.toUpperCase()}';


    listMap();
  }
}
