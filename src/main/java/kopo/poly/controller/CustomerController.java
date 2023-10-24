package kopo.poly.controller;

import kopo.poly.dto.CustomerDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICustomerService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;
    @GetMapping(value = "/login")
    public String login() {

        log.info(this.getClass().getName() + ".customerLogin");

        return "/customer/login";
    }
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".loginProc Start!");

        int res = 0; //로그인 성공 1, 아이디 비밀번호 불일치 0, 에러 2
        String msg = ""; //결과 메시지
        MsgDTO dto = null; 

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        CustomerDTO pDTO = null;

        try {

            String id = CmmUtil.nvl(request.getParameter("id")); //아이디
            String pw = CmmUtil.nvl(request.getParameter("pw")); //비밀번호

            log.info("id : " + id);
            log.info("pw : " + pw);

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new CustomerDTO();

            pDTO.setId(id);

            //비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));

            // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 customerService 호출하기
            CustomerDTO rDTO = customerService.getLogin(pDTO);

            if (CmmUtil.nvl(rDTO.getId()).length() > 0) { //로그인 성공

                res = 1;

                msg = "로그인이 성공했습니다.";

                session.setAttribute("SS_USER_ID", id);
                session.setAttribute("SS_USER_NAME", CmmUtil.nvl(rDTO.getName()));

            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";

            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            // 결과 메시지 전달하기
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".loginProc End!");
        }

        return dto;
    }


    @GetMapping(value = "/userIndex")
    public String customerIndex() {
        log.info("start!");
        return "/customer/userIndex";
    }

    @GetMapping(value = "/cart")
    public String cart() {
        log.info("start!");
        return "/customer/cart";
    }

    @GetMapping(value = "/userSignUp")
    public String userSignUp() {
        log.info(this.getClass().getName() + "userSignUp");
        return "/customer/userSignUp";
    }

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public CustomerDTO getUserIdExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("id : " + id);

        CustomerDTO pDTO = new CustomerDTO();

        pDTO.setId(id);

        CustomerDTO rDTO = Optional.ofNullable(customerService.getUserIdExists(pDTO)).orElseGet(CustomerDTO::new);
        log.info(this.getClass().getName() + ".getUserIdExists End!");
        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertCustomer")
    public MsgDTO insertCustomer(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertCustomer Start!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CustomerDTO pDTO = null;

        try {
            String id = CmmUtil.nvl(request.getParameter("id"));
            String pw = CmmUtil.nvl(request.getParameter("pw"));
            String pn = CmmUtil.nvl(request.getParameter("pn"));
            String name = CmmUtil.nvl(request.getParameter("name"));
            String age = CmmUtil.nvl(request.getParameter("age"));
            String type = CmmUtil.nvl(request.getParameter("type"));

            log.info("id : " + id);
            log.info("pw : " + pw);
            log.info("pn : " + pn);
            log.info("name : " + name);
            log.info("age : " + age);
            log.info("type : " + type);

            pDTO = new CustomerDTO();

            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            pDTO.setPn(pn);
            pDTO.setName(name);
            pDTO.setAge(age);
            pDTO.setType(type);

            log.info(pDTO.toString());

            res = customerService.insertCustomer(pDTO);

            log.info("res : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다";
            } else {
                msg = "오류로 인해 회원가입에 실패하였습니다";
            }
        }catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
            e.printStackTrace();
        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            dto.setResult(res);
            log.info(this.getClass().getName() + ".insertCustomer End!");
        }
        return dto;
    }
    @GetMapping(value = "/shop")
    public String shop() {
        log.info("start!");
        return "/customer/shop";
    }

    @GetMapping(value = "/map")
    public String map() {
        log.info("start!");
        return "/customer/map";
    }

    @GetMapping(value = "/market")
    public String market() {
        log.info("start!");
        return "/customer/market";
    }

    @GetMapping(value = "/chat")
    public String chat() {
        log.info("start!");
        return "/customer/chat";
    }

    @GetMapping(value = "/single-product")
    public String singleProduct() {
        log.info("start!");
        return "/customer/single-product";
    }
}
