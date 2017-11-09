# spring-boot-excel-upload-demo

Demo project on how upload and process excel file in the spring boot

# Features

* Upload and process Excel file
* Upload and process large CSV file
* Allow Async callback and websocket to report progress on loading data into database (mocked database used here)
* Allow Large file upload and long running process using guava concurrency

# Usage

## If you are using IntelliJ

Open the project in IntelliJ

Go to src/java/com.github.chen0040.bootslingshot and right-click SpringSlingshotApplication and select "Run main()" in IntelliJ

Navigate to your web browser to http://localhost:8080

## If you are uploading a CSV file

Once at http://localhost:8080, click "CSV Sample Download" to download a csv sample, then click "Choose File" to load the
downloaded CSV sample,and then click "Upload CSV", you will notice that as the product is being saved on the remote server,
the web page keeps on updating the progress (done using websocket and sockjs). At the backend, the Thread.sleep is used to simulate
long running process.

## If you are uploading a Excel file

Once at http://localhost:8080, click "Excel Sample Download" to download a csv sample, then click "Choose File" to load the
downloaded Excel sample,and then click "Upload Excel", you will notice that as the product is being saved on the remote server,
the web page keeps on updating the progress (done using websocket and sockjs). At the backend, the Thread.sleep is used to simulate
long running process.
