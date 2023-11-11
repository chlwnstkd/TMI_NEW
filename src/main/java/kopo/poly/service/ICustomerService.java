package kopo.poly.service;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.TraderDTO;

public interface ICustomerService  {
    CustomerDTO getCustomerIdExists(CustomerDTO pDTO) throws Exception;
    CustomerDTO getLogin(CustomerDTO pDTO) throws Exception;
    int insertCustomer(CustomerDTO pDTO) throws Exception;

    CustomerDTO getEmailExists(CustomerDTO pDTO) throws Exception;

    CustomerDTO searchEmail(CustomerDTO pDTO) throws Exception;

    CustomerDTO searchCustomerIdOrPasswordProc(CustomerDTO pDTO) throws Exception;

    CustomerDTO getCustomerInfo(CustomerDTO pDTO) throws Exception;

    int changeCustomer(CustomerDTO pDTO) throws Exception;

    int changePw(CustomerDTO pDTO) throws Exception;
}
