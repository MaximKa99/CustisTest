### Что за проект?
Тестовое задание для компании CUSTIS на позицию Junior Java Developer.
Приложение выполняет сортировку файла с большим количеством строк, используя внешнюю сортировку слиянием.

### Что необходимо для запуска проекта?
- Java 8
- Maven 3.x.x

### Как запустить?
- Сначала клоним проект
    `git clone https://github.com/MaximKa99/CustisTest.git`
- Потом переходим в папку с проектом в консоли
    `cd path/to/dir`
- Запускаем :
    - `mvn clean compile`
  - `mvn exec:java -Dexec.mainClass=com.custis.sorter.App -Dexec.args="amountOfStokes maxLengthOfSingleStroke"`

Приложение запустится, создаст файл source с рандомными строчками в соответсвии с переданными параметрами, а затем отсортирует
их в файл sorted

### Автор
@https://github.com/MaximKa99
