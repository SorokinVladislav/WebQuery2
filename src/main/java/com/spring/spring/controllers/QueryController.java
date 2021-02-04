package com.spring.spring.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.spring.spring.MDLPQuery;
import com.spring.spring.jsonCreator;
import com.spring.spring.models.condot.*;
import com.spring.spring.repo.condot.*;
import com.spring.spring.report;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.spring.spring.ConfigFile.*;
import static com.spring.spring.MDLPToken.MDLPBalance;
import static com.spring.spring.SendMessageToMDLP.sendEmailWithAttachments;


@Controller
public class QueryController {
    @Autowired
    private docLogRepo docLogRepo;

    @Autowired
    private packDataRepo packDataRepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private dataUploadRepo dataUploadRepo;

    @Autowired
    private pkglevelrepo pkgLevelRepo;

    @Autowired
    private OrderBookingRepo orderBookingRepo;


    @GetMapping("/suz")
    public String suz(Model model) throws IOException, InterruptedException {
        Iterable<tbl_crpt_orderbookingmaster> orders = orderBookingRepo.findSUZStatus();
        model.addAttribute("orders", orders);
        return "suz";
    }


    @PostMapping("/json")
    public String json(@AuthenticationPrincipal tbl_users user, @RequestParam String SSCC,
                       Model model) throws SQLException, FileNotFoundException {
        if (user.getRoles().equals("1")) {
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " send report to TraceWay"));
            String json = jsonCreator.json(SSCC);
            model.addAttribute("json", json);
            model.addAttribute("sscc", SSCC);
            model.addAttribute("balance", MDLPBalance);
            return "jsonCreator";
        } else
            return "jsonCreator";
    }

    @PostMapping("/mdlp")
    public String mdlp(@RequestParam String SSCC,
                       Model model) throws SQLException, IOException {
        String json = jsonCreator.mdlp(SSCC);
        JSONObject jsonFromMDLP = new JSONObject(json);
        List<tbl_production_packaging_data> list = packDataRepo.findByUID(SSCC);
        model.addAttribute("jid", list.get(0).getJobid());
        model.addAttribute("balance", MDLPBalance);

        JSONArray array;
        try {
            array = jsonFromMDLP.getJSONArray("tree").getJSONObject(0).getJSONArray("childrens");
            model.addAttribute("code", "Код: " + jsonFromMDLP.get("code"));
            model.addAttribute("grpcnt", "Групповые коды: " + jsonFromMDLP.get("grpcnt"));
            model.addAttribute("indcnt", "Индивидуальные коды: " + jsonFromMDLP.get("indcnt"));
            model.addAttribute("array", array);

        } catch (Exception e) {
            model.addAttribute("code", "Код не найден!");
        }

//        model.addAttribute("json", json);
        model.addAttribute("sscc", SSCC);

        return "jsonCreator";
    }

    @PostMapping("/tw")
    public String tw(@RequestParam String SSCC,
                     Model model) throws SQLException, IOException {

        String json = jsonCreator.tw(SSCC);
        JSONObject jsonFromMDLP = new JSONObject(json);
        JSONArray array = null;
        ArrayList<String> listWithCodes = new ArrayList<>();
        List<tbl_production_packaging_data> list = packDataRepo.findByUID(SSCC);
        model.addAttribute("jid", list.get(0).getJobid());
        model.addAttribute("balance", MDLPBalance);
        try {
            array = jsonFromMDLP.getJSONArray("codes");
            for (int i = 0; i < array.length(); i++) {
                listWithCodes.add(array.getJSONObject(i).get("code").toString());
            }
            model.addAttribute("array", listWithCodes);
            model.addAttribute("code", "Код: " + array.getJSONObject(0).get("parent_code"));
            model.addAttribute("grpcnt", "Количество: " + array.length());
            model.addAttribute("indcnt", "Наименование: " + array.getJSONObject(0).get("nomenclature"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("code", "Код не найден!");
        }

//        model.addAttribute("json", json);
        model.addAttribute("sscc", SSCC);

        return "jsonCreator";
    }


    @PostMapping("/sendjson")
    public String sendJson(@AuthenticationPrincipal tbl_users user, @RequestParam String textarea,
                           Model model) throws SQLException, IOException {
        if (user.getRoles().equals("1")) {
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " send report to TraceWay"));

            String twresponse = jsonCreator.sendToTW(textarea);
            model.addAttribute("twresponse", twresponse);
            model.addAttribute("balance", MDLPBalance);

            return "jsonCreator";
        } else
            return "jsonCreator";
    }


    @GetMapping("/json")
    public String json(Model model) {
        model.addAttribute("balance", MDLPBalance);
        return "jsonCreator";
    }


    @GetMapping("/jobs")
    public String jobMain(Model model) {
        Iterable<tbl_job> posts = jobRepo.findALL4();
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/batch")
    public String searchByBatch(@RequestParam String batch, Model model) {
        Iterable<tbl_job> posts = jobRepo.findByBatch(batch);
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/jobname")
    public String searchByJobname(@RequestParam String jobname, Model model) {
        Iterable<tbl_job> posts = jobRepo.findByJobname(jobname);
        model.addAttribute("posts", posts);
        return "jobs";
    }


    @GetMapping("/closed")
    public String closedJobs(Model model) {
        Iterable<tbl_job> posts = jobRepo.findClosedJobs();
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/suspend")
    public String suspendJobs(Model model) {
        Iterable<tbl_job> posts = jobRepo.findSuspendJobs();
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/mistakes")
    public String jobsWithMistakes(Model model) {
        Iterable<tbl_crpt_documentlog> posts = docLogRepo.findJobsWithMistakes();

        model.addAttribute("postss", posts);

        return "mistakes";
    }

    @GetMapping("/codeswaiting")
    public String codeswaiting(Model model) {
        List<tbl_job> posts = jobRepo.codeswaiting();
        model.addAttribute("posts", posts);
        model.addAttribute("count", "Количество работ в ожидании кодов: " + posts.size());
        return "jobs";
    }

    @GetMapping("/withonereport")
    public String withonereport(Model model) {
        Iterable<tbl_job> posts = jobRepo.withonereport();
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/jobid")
    public String searchByJobId(@RequestParam String jobid, Model model) {
        Iterable<tbl_job> posts = jobRepo.findByJobID(jobid);
        model.addAttribute("posts", posts);
        return "jobs";
    }

    @GetMapping("/jobs/{jid}")
    public String blogDetails(@PathVariable(value = "jid") Long jid, Model model) {

        Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid.toString());
        model.addAttribute("postss", posts);
        Iterable<tbl_job> query = jobRepo.findJobDetails(jid.toString());
        Iterable<tbl_crpt_dataupload> query2 = dataUploadRepo.findReportId(jid.toString());

        model.addAttribute("reports", query2);
        try {
            tbl_job log = query.iterator().next();
            model.addAttribute("product_name", log.getProduct_name());
            model.addAttribute("jobstatus", log.getJobstatus());
            model.addAttribute("jobname", log.getjobname());

        } catch (Exception e) {
        }


        return "jobs-details";
    }

    File file;
    //map report

    @GetMapping("/jobs/{jid}/mapreport")
    public String mapReport(@PathVariable(value = "jid") String jid, Model model) throws IOException, SQLException {

        Iterable<tbl_production_packaging_data> posts = packDataRepo.findByJIDD(jid);
        file = report.mapReport(jid, posts);

        return "redirect:/Map_Report_JobID-{jid}.xlsx";
    }

    @RequestMapping(path = "/Map_Report_JobID-{jid}.xlsx", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException {

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/x-excel"))
                .body(resource);
    }

    //detailed job report
    @GetMapping("/jobs/{jid}/detailedjob")
    public String detailedJob(@PathVariable(value = "jid") String jid, Model model) throws IOException, SQLException, InterruptedException {

        Iterable<tbl_production_packaging_data> posts = packDataRepo.findByJIDD(jid);
        //search pkg id

        Iterable<tbl_product_pkg_level_details> pakg = pkgLevelRepo.findPkgLVL(jid);

        file = report.detailedJob(jid, posts, pakg);
        return "redirect:/Detailed_Job_JobID-{jid}.xlsx";
    }

    @RequestMapping(path = "/Detailed_Job_JobID-{jid}.xlsx", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadReport(String param) throws IOException {

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/x-excel"))
                .body(resource);
    }

// reject job

    @GetMapping("/jobs/{jid}/reject")
    public String rejectJob(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, Model model) {
        if (user.getRoles().equals("1") || user.getUsername().equals("valeriy.zababurin") || user.getUsername().equals("segl") || user.getUsername().equals("anlu")) {
            jobRepo.rejectJob(jid);
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " set job status \"Reject\", JobID - " + jid));
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            try {
                tbl_crpt_documentlog log = posts.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getJobname());
            } catch (Exception e) {
            }
            return "redirect:/jobs/{jid}";
        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }

    // uidgenerated job
    @GetMapping("/jobs/{jid}/uidgenerated")
    public String uidgeneratedJob(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, Model model) {
        if (user.getRoles().equals("1") || user.getRoles().equals("5") || user.getUsername().equals("valeriy.zababurin") || user.getUsername().equals("segl") || user.getUsername().equals("anlu")) {
            List<tbl_crpt_documentlog> list = docLogRepo.is213Success(jid);
            if (list.size() > 0) {
                jobRepo.uidgeneratedJob(jid);
                TelegramBot bot = new TelegramBot(telBotToken);
                bot.execute(new SendMessage(telChatId, user.getUsername() + " set job status \"UID Generated\", JobID - " + jid));
            } else {
                model.addAttribute("empty", "Данный работник не зарегистрирован");
                Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
                model.addAttribute("postss", posts);
                Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
                model.addAttribute("jid", jid);
                try {
                    tbl_job log = query.iterator().next();
                    model.addAttribute("product_name", log.getProduct_name());
                    model.addAttribute("jobstatus", log.getJobstatus());
                    model.addAttribute("jobname", log.getjobname());
                } catch (Exception e) {
                }
                return "jobs-details";
            }
            return "redirect:/jobs/{jid}";

        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }

    // suspend job

    @GetMapping("/jobs/{jid}/suspend")
    public String suspendJob(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, Model model) {
        if (user.getRoles().equals("1") || user.getUsername().equals("valeriy.zababurin") || user.getUsername().equals("segl") || user.getUsername().equals("anlu")) {

            jobRepo.suspendJob(jid);
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " set job status \"Suspend\", JobID - " + jid));
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            try {
                tbl_crpt_documentlog log = posts.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getJobname());
            } catch (Exception e) {
            }
            return "redirect:/jobs/{jid}";
        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }

    }

    @GetMapping("/jobs/{jid}/{xmltype}")
    public String reportDetails(@PathVariable(value = "jid") String jid, @PathVariable(value = "xmltype") String xmltype, Model model) {

        String xmltypee = xmltype.replaceAll("\\D", "");
        Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDDandXMLType(jid, xmltypee);

        model.addAttribute("postss", posts);
        model.addAttribute("jid", jid);
        model.addAttribute("xmltype", xmltypee);
        return "reportDetails";
    }

    @GetMapping("/jobs/{jid}/{xmltype}/resend")
    public String resendReport(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, @PathVariable(value = "xmltype") String xmltype, Model model) {
        if (user.getRoles().equals("1")) {
            docLogRepo.resendReport(jid, xmltype);
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " resend " + xmltype + ", JobID - " + jid));
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDDandXMLType(jid, xmltype);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            model.addAttribute("xmltype", xmltype);
            return "redirect:/jobs/{jid}";
        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }


    @PostMapping("/jobs/{jid}/{xmltype}/sendmessagetomdlp")
    public String sendMessageToMDLP(@AuthenticationPrincipal tbl_users user, @RequestParam String jid,
                                    @RequestParam String xmltype, @RequestParam String document_id,
                                    @RequestParam String document_receipt, Model model) throws IOException, InterruptedException {
        if (user.getRoles().equals("1")) {

            String host = "smtp.office365.com";
            String port = "587";
            String mailFrom = mailFromThis;
            String password = mailPassword;

            // message info
            String mailTo = mailToThis;
            String subject = mailSubject;

            String message = messageText(document_id, document_receipt);

            String[] attachFiles = new String[2];
            attachFiles[0] = MDLPQuery.getResponse(document_id).toPath().toString();
            attachFiles[1] = MDLPQuery.getRequest(document_id).toPath().toString();
            ;

            try {
                sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                        subject, message, attachFiles);
                System.out.println("Email sent.");
            } catch (Exception ex) {
                System.out.println("Could not send email.");
                ex.printStackTrace();
            }

            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " sent message to MDLP " + xmltype + ", JobID - " + jid));
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDDandXMLType(jid, xmltype);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            model.addAttribute("xmltype", xmltype);
            return "redirect:/jobs/{jid}";


        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }


    @GetMapping("/jobs/{jid}/{xmltype}/status7")
    public String reportStatus7(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, @PathVariable(value = "xmltype") String xmltype, Model model) {
        if (user.getRoles().equals("1")) {
            docLogRepo.setJobStatus7(jid, xmltype);
            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " set Status=7, XmlType - " + xmltype + ", JobID - " + jid));
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDDandXMLType(jid, xmltype);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            model.addAttribute("xmltype", xmltype);
            return "redirect:/jobs/{jid}";
        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }

    @GetMapping("/jobs/{jid}/{xmltype}/resend9151")
    public String resend9151(@AuthenticationPrincipal tbl_users user, @PathVariable(value = "jid") String jid, @PathVariable(value = "xmltype") String xmltype, Model model) throws InterruptedException {
        if (user.getRoles().equals("1")) {
            docLogRepo.resend9151_1(jid);
            docLogRepo.resend9151_2(jid);
            Thread.sleep(10000);
            docLogRepo.resend9151_3(jid);

            TelegramBot bot = new TelegramBot(telBotToken);
            bot.execute(new SendMessage(telChatId, user.getUsername() + " resend 915 and 10311, JobID - " + jid));

            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDDandXMLType(jid, xmltype);
            model.addAttribute("postss", posts);
            model.addAttribute("jid", jid);
            model.addAttribute("xmltype", xmltype);
            return "redirect:/jobs/{jid}";
        } else {
            model.addAttribute("noaccess", "noaccess");
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid);
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid);
            model.addAttribute("jid", jid);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());
            } catch (Exception e) {
            }
            return "jobs-details";
        }
    }


    @GetMapping("/jobs/{jid}/{xmltype}/getresponse/{document_id}")
    public ResponseEntity<Resource> getresponse(@PathVariable(value = "document_id") String document_id) {
        try {
            File file = MDLPQuery.getResponse(document_id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("content-disposition", "attachment; filename=" + document_id + ".txt");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/x-excel"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/jobs/{jid}/{xmltype}/getrequest/{document_id}")
    public ResponseEntity<Resource> getRequest(@AuthenticationPrincipal tbl_users user,
                                               @PathVariable(value = "document_id") String document_id, Model model) {
        try {
            File file = MDLPQuery.getRequest(document_id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("content-disposition", "attachment; filename=" + document_id + ".txt");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/x-excel"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @GetMapping("/getresponsefrom10311/{document_id}")
    public ResponseEntity<Resource> getresponsefrom10311(@PathVariable(value = "document_id") String document_id) {
        try {
            String docID = MDLPQuery.getDocIDfromSUZid(document_id);
            File file = MDLPQuery.getResponse(docID);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("content-disposition", "attachment; filename=" + document_id + ".txt");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/x-excel"))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @GetMapping("/jobs/{jid}/mdlpcount")
    public String mdlpcount(@PathVariable(value = "jid") String jid, Model model) throws IOException {
        try {
            Iterable<tbl_job> posts = jobRepo.findJobDetails(jid);
            tbl_job log = posts.iterator().next();
            System.out.println(log.gtin + "  " + log.batchno);
            String count = MDLPQuery.getMDLPCount(log.gtin, log.batchno);
            System.out.println(count);
            model.addAttribute("count", count);
            Iterable<tbl_crpt_documentlog> posts2 = docLogRepo.findByJIDD(jid.toString());
            model.addAttribute("postss", posts2);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid.toString());
            Iterable<tbl_crpt_dataupload> query2 = dataUploadRepo.findReportId(jid.toString());

            model.addAttribute("reports", query2);
            try {
                tbl_job log2 = query.iterator().next();
                model.addAttribute("product_name", log2.getProduct_name());
                model.addAttribute("jobstatus", log2.getJobstatus());
                model.addAttribute("jobname", log2.getjobname());

            } catch (Exception e) {
            }
            return "jobs-details";
        } catch (Exception e) {
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid.toString());
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid.toString());
            Iterable<tbl_crpt_dataupload> query2 = dataUploadRepo.findReportId(jid.toString());

            model.addAttribute("reports", query2);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());

            } catch (Exception ex) {
            }
            return "jobs-details";
        }
    }


    @GetMapping("/jobs/{jid}/tracewaycount")
    public String TraceWaycount(@PathVariable(value = "jid") String jid, Model model) throws IOException {
        try {
            Iterable<tbl_job> posts = jobRepo.findJobDetails(jid);
            tbl_job log = posts.iterator().next();
            System.out.println(log.gtin + "  " + log.batchno);
            Integer count = jsonCreator.getTWCount(log.gtin, log.batchno);
            System.out.println(count);
            model.addAttribute("countTW", count);
            Iterable<tbl_crpt_documentlog> posts2 = docLogRepo.findByJIDD(jid.toString());
            model.addAttribute("postss", posts2);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid.toString());
            Iterable<tbl_crpt_dataupload> query2 = dataUploadRepo.findReportId(jid.toString());

            model.addAttribute("reports", query2);
            try {
                tbl_job log2 = query.iterator().next();
                model.addAttribute("product_name", log2.getProduct_name());
                model.addAttribute("jobstatus", log2.getJobstatus());
                model.addAttribute("jobname", log2.getjobname());

            } catch (Exception e) {
            }
            return "jobs-details";
        } catch (Exception e) {
            Iterable<tbl_crpt_documentlog> posts = docLogRepo.findByJIDD(jid.toString());
            model.addAttribute("postss", posts);
            Iterable<tbl_job> query = jobRepo.findJobDetails(jid.toString());
            Iterable<tbl_crpt_dataupload> query2 = dataUploadRepo.findReportId(jid.toString());

            model.addAttribute("reports", query2);
            try {
                tbl_job log = query.iterator().next();
                model.addAttribute("product_name", log.getProduct_name());
                model.addAttribute("jobstatus", log.getJobstatus());
                model.addAttribute("jobname", log.getjobname());

            } catch (Exception ex) {
            }
            return "jobs-details";
        }
    }


}




