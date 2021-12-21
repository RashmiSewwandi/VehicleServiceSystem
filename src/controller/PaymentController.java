package controller;

import Model.Payment;
import Model.ServiceDetail;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentController {
    public String getPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                        "SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId +1;
            if (tempId < 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {
                return "P-0" + tempId;
            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }

    public boolean Payment(Payment payment)  {
        Connection con=null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm= con.
                    prepareStatement("INSERT INTO payment VALUES(?,?,?,?)");

            stm.setObject(1, payment.getPaymentId());
            stm.setObject(2, payment.getNIC());
            stm.setObject(3, payment.getPaymentDate());
            stm.setObject(4, payment.getPaymentTime());



            if(stm.executeUpdate()>0){
                if(saveServiceDetail(payment.getPaymentId(),payment.getService())){
                    con.commit();
                    return true;

                }else{
                    con.rollback();
                    return false;
                }

            }else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean saveServiceDetail(String paymentId, ArrayList<ServiceDetail> service) throws SQLException, ClassNotFoundException {
        for (ServiceDetail temp : service
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO servicedetail VALUES(?,?,?,?,?)");
            stm.setObject(1, temp.getVehicleNumber());
            stm.setObject(2, paymentId);
            stm.setObject(3, temp.getServiceType());
            stm.setObject(4, temp.getItemReplace());
            stm.setObject(5, temp.getServiceCharge());

            if (stm.executeUpdate() > 0) {
            } else {
                return false;
            }
          System.out.println(temp);
        }
        return true;

    }


}
