
package  com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.Employee;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employees {
    public  static ApiInterface apiinterface1;

   public void GetEmployeeByName(String CompanyDbName, String Url,String username, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
     
        Call<Employee> call = apiinterface1.GetEmployeeByUserName(Url,CompanyDbName,username);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
Employee employee=new Employee();
                   employee=response.body();
                    customCallback.onSucess(employee);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

                   customCallback.onFailure(t.getMessage());
               

            }


        });

    }


    public void Login(String CompanyDbName, String Url, String username, String password, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Employee> call = apiinterface1.Login(Url,CompanyDbName,username,password);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Employee employee=new Employee();
                    employee=response.body();
                    customCallback.onSucess(employee);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }
    public void GetEmployeeById(String CompanyDbName, String Url,long Id, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Employee> call = apiinterface1.GetEmployeeById(Url,CompanyDbName,Id);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Employee employee=new Employee();
                    employee=response.body();
                    customCallback.onSucess(employee);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

}
