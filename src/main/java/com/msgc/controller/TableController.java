package com.msgc.controller;

import com.msgc.cache.FieldTypeFlyweightFactory;
import com.msgc.config.WebMvcConfig;
import com.msgc.constant.FilePath;
import com.msgc.constant.SessionKey;
import com.msgc.constant.enums.MessageTypeEnum;
import com.msgc.constant.enums.TableStatusEnum;
import com.msgc.constant.response.ResponseWrapper;
import com.msgc.entity.*;
import com.msgc.entity.bo.ExcelReadStrategy;
import com.msgc.entity.dto.FieldDTO;
import com.msgc.entity.dto.StrategyParam;
import com.msgc.exception.ResourceNotFoundException;
import com.msgc.service.*;
import com.msgc.utils.*;
import com.msgc.utils.excel.ExcelUtilAdapter;
import com.msgc.utils.qrCode.QrCodeUtilAdapter;
import com.msgc.utils.zip.ZipUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
*Type: TableController
* Description: 收集表相关
* @author LMM
 */
@Slf4j
@Controller
@RequestMapping("/collect")
public class TableController {

	private final ITableService tableService;
	private final IFieldService fieldService;
	private final IAnswerRecordService answerRecordService;
	private final IAnswerService answerService;
    private final IMessageService messageService;

    // 一周的毫秒数
	private final long ONE_WEEK_TIME = 7 * 24 * 60 * 60 * 1000;
	// 默认的截止时间，当前时间 + 一周
	private final long DEFAULT_END_TIME = ONE_WEEK_TIME;

    @Autowired
    public TableController(ITableService tableService, IFieldService fieldService, IAnswerRecordService answerRecordService, IAnswerService answerService, IMessageService messageService) {
        this.tableService = tableService;
        this.fieldService = fieldService;
        this.answerRecordService = answerRecordService;
        this.answerService = answerService;
        this.messageService = messageService;
    }


    //选择新建方式页面
	@GetMapping("/new")
	public String newCollectPage() {
		return "collect/newCollect";
	}

    //在线制作页面
    @GetMapping("/new/byOnlineProduction")
    public String newByOnlinePage(){
        return "collect/buildCollect";
    }

    //填写完毕页面
    @GetMapping("/finish")
    public String finishPage(){
        return "collect/finish";
    }

    //在线编辑
    @GetMapping("/edit")
    public String editOnlinePage(HttpSession session, HttpServletRequest request, Model model){
	    User user = (User)session.getAttribute(SessionKey.USER);
	    if(user != null){
            int tid = Integer.parseInt(request.getParameter("t"));
            Table table = tableService.findById(tid);
            if(table != null && user.getId().equals(table.getOwner())){
                model.addAttribute("table", table);
                if(TableStatusEnum.EDIT.equal(table.getState())){
                    List<Field> fieldList = fieldService.findAllByTableId(table.getId());
                    model.addAttribute("fieldList", fieldList);
                    return "collect/buildCollect";
                }else if(TableStatusEnum.COLLECTING.equal(table.getState())){
                    //如果该表在收集中，跳到收集页面
                    return "redirect:/collect/share/" + table.getId();
                }
            }
        }
        throw new ResourceNotFoundException();
    }

    //复制表
    @GetMapping("/new/byCopy/{tableId}")
    public String newByCopyPage(@PathVariable Integer tableId, Model model){
	    User user = (User)WebUtil.getSessionKey(SessionKey.USER);
	    if(user != null){
            Table table = new Table();
            // spring 包中的该方法是讲前者赋值给后者
            BeanUtils.copyProperties(tableService.findById(tableId), table);
            if(user.getId().equals(table.getOwner())){
                List<Field> fieldList = fieldService.findAllByTableId(table.getId());
                //初始化新表的数值
                table.setId(null);
                table.setState(TableStatusEnum.EDIT.getValue());
                table.setPublishTime(null);
                table.setCreateTime(null);
                Date nowDate = new Date();
                table.setStartTime(nowDate);
                table.setEndTime(new Date(nowDate.getTime() + DEFAULT_END_TIME));
                table.setFilledNum(0);
                table.setSecretKey(null);
                model.addAttribute("table", table);
                model.addAttribute("fieldList", fieldList);
                return "collect/buildCollect";
            }
        }
        throw new ResourceNotFoundException();
    }


    //展示当前登录用户所拥有的所有表
	@GetMapping("/my")
	public String selfCollectPage(HttpSession session, Model model) {
        User owner = (User)session.getAttribute(SessionKey.USER);
        List<Table> tableList = tableService.findAllActiveTable(owner.getId());
        tableList = tableList.stream().map(TableStatusEnum::processTableState).collect(Collectors.toList());
        model.addAttribute("tableList", tableList);
		return "collect/myCollect";
	}

	//展示当前用户所填写过的表
	@GetMapping("/filled")
	public String filledCollectPage(HttpSession session, Model model) {
        User user = (User)session.getAttribute(SessionKey.USER);
        if(user != null){
            List<AnswerRecord> answerRecordList = answerRecordService.findAllByUserId(user.getId());
            //将 answerRecordList 中的 record 提取出所有的 id 并以 List 的形式返回
            List<Integer> tableIdList = answerRecordList.stream().map(AnswerRecord::getTableId).collect(Collectors.toList());
            List<Table> tableList = tableService.findAllById(tableIdList);
            Stream<Table> tableStream = tableList.stream().filter(table -> !TableStatusEnum.DELETE.getValue().equals(table.getState()));
            tableList = tableStream.collect(Collectors.toList());
            List<String> ipList = answerRecordList.stream().map(a -> IPUtil.intToIPv4Str(a.getIp())).collect(Collectors.toList());
            model.addAttribute("tableList", tableList);
            model.addAttribute("recordList", answerRecordList);
            model.addAttribute("ipList", ipList);
        }else {
            return "redirect:/login?redirectUrl=%2fcollect%2ffilled";
        }
		return "collect/filledCollect";
	}


	/**
	 * 制作表、编辑表的提交动作，成功代表保存到数据库
	 * @param request 读取发送来的参数
	 * @return 返回是否保存成功以及提示信息
	 */
    @Transactional(rollbackFor = Exception.class)
	@ResponseBody
	@PostMapping("/new/byOnlineProduction/tableForm.action")
	public String newByOnline(HttpServletRequest request) throws ParseException {
		if(StringUtils.isEmpty(request.getParameter("table-item-index-array"))){
			return JsonUtil.toJson(ResponseWrapper.fail("项不能为空"));
		}
        Table table;
        table = receiveTableFromFront();//解析失败时抛出异常，数据回滚
        table = tableService.save(table);
		Integer tableId = table.getId();
		if(tableId == null){
			return JsonUtil.toJson(ResponseWrapper.fail("保存出错"));
		}
        List<Field> fieldList = receiveFieldsFromFront(tableId);
        fieldService.save(fieldList);
		return JsonUtil.toJson(ResponseWrapper.success("已保存"));
	}

	//接收页面传来的 table 对象 公共部分，使用需要tryCatch
	private Table receiveTableFromFront() throws ParseException {
        HttpServletRequest request = WebUtil.getRequest();
        User user = (User)request.getSession().getAttribute(SessionKey.USER);
        Table table = new Table();
        Date nowDateTime = new Date();
        //判断是否是修改
        String strTableId = request.getParameter("table-id");
        if(StringUtils.isNotEmpty(strTableId)){
            Integer tableId = Integer.parseInt(strTableId);
            BeanUtils.copyProperties(tableService.findById(tableId), table);
            //修改一个不存在的收集表
            if (table.getId() == null){
                throw new ResourceNotFoundException();
            }
            //非本人修改
            if(! user.getId().equals(table.getOwner())){
                throw new ResourceNotFoundException();
            }
            //合法修改表，删掉这个表之前所有的 fields
            if(!fieldService.deleteByTableId(table.getId())){
                log.info("删除无效表字段失败！tableId - " + table.getId());
            }
        }
        //该表是新增而不是修改，设定某些初始值
        else{
            table.setOwner(((User)request.getSession().getAttribute(SessionKey.USER)).getId());
            table.setCreateTime(nowDateTime);
            table.setState(TableStatusEnum.EDIT.toString());
            table.setFilledNum(0);
            table.setCreateTime(nowDateTime);
        }
        table.setName(request.getParameter("table-name"));
        table.setUpdateTime(nowDateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        table.setStartTime(sdf.parse(request.getParameter("table-time-start")));
        table.setEndTime(sdf.parse(request.getParameter("table-time-end")));
        table.setVisibility("on".equalsIgnoreCase(request.getParameter("table-visibility")));
        table.setMaxFillNum(StringUtils.isEmpty(request.getParameter("table-maxFillNum")) ?0:Integer.parseInt(request.getParameter("table-maxFillNum")));
        if(table.getStartTime().getTime() >= table.getEndTime().getTime() || nowDateTime.getTime() >= table.getEndTime().getTime())
            throw new ParseException("时间设置出错", 0);
        return table;
    }

    //接收页面传来的 field 对象 公共部分，使用需要tryCatch
    private List<Field> receiveFieldsFromFront(int tableId){
        HttpServletRequest request = WebUtil.getRequest();
        List<Field> fieldList = new ArrayList<>();
        String[] indexArray = request.getParameter("table-item-index-array").split("-");
        Field field;
        int fieldNum = 1;
        for (String n : indexArray) {
            field = new Field();
            field.setType(request.getParameter("table-item-type-" + n));
            field.setTableId(tableId);
            field.setNum(fieldNum);
            field.setName(request.getParameter("table-item-name-" + n));
            field.setMaxLength(StringUtils.isEmpty(request.getParameter("table-item-maxlength-" + n)) ? 220 :Integer.parseInt(request.getParameter("table-item-maxlength-" + n)));
            field.setDefaultValue(request.getParameter("table-item-default-" + n));
            field.setRequired("on".equalsIgnoreCase(request.getParameter("table-item-required-" + n)));
            field.setVisibility("on".equalsIgnoreCase(request.getParameter("table-item-visibility-" + n)));
            fieldList.add(field);
            fieldNum ++;
        }
        return fieldList;
    }

    //进入填写收集表页面，并判断是否输入 暗号
    @GetMapping("/share/start")
    public String startInputCollectPage(HttpServletRequest request, Model model, HttpSession session) {
        int tid = Integer.parseInt(request.getParameter("t"));
        try {
            Table table = tableService.findById(tid);
            Date now = new Date();
            if (TableStatusEnum.EDIT.equal(table.getState())){
                model.addAttribute("resultMessage", "表主人未准备好哦~");
                return "/displayMessage";
            }else if(!TableStatusEnum.COLLECTING.equal(table.getState())){
                model.addAttribute("resultMessage", "表已经截止啦~");
                return "/displayMessage";
            }
            if(now.getTime() < table.getStartTime().getTime()){
                model.addAttribute("resultMessage", "还未到开始时间哦~");
                return "/displayMessage";
            }else if(now.getTime() > table.getEndTime().getTime()){
                model.addAttribute("resultMessage", "该表已截止收集啦~");
                return "/displayMessage";
            }
            //是否需要输入暗号
            if(table.getSecretKey() != null){
                model.addAttribute("table_id", table.getId());
                return "collect/initCollect";
            }
            //不需要输入，返回收集表填写页面
            return inputPage(table, model, (User)session.getAttribute(SessionKey.USER));
        }catch (Exception e){
            e.printStackTrace();
            throw new ResourceNotFoundException();
        }
    }
	/**
	 * 进入分享的收集表表前需要输入暗号，提交时暗号检查
	 * @return 输入正确就进入，否则继续填写
	 */
	@PostMapping("/share/checkKey.action")
	public String checkKeyPage(HttpServletRequest request, Model model, HttpSession session) {
		int tid = Integer.parseInt(request.getParameter("table_id"));
		Table table = tableService.findById(tid);
		String key = request.getParameter("key");
		//检查暗号 key 是否正确
		if(RegexCheckUtils.checkTableKey(key) && table != null && key.equals(table.getSecretKey())){
			//不需要输入，返回收集表填写页面
			return inputPage(table, model, (User)session.getAttribute(SessionKey.USER));
		}
		//暗号 key 错误，返回填写暗号页面
		model.addAttribute("table_id", tid);
		return "collect/initCollect";
	}

	// /share/start /share/checkKey.action 公共部分，返回填写收集表页面
	private String inputPage(Table table, Model model, User user){
        List<Field> fieldList = fieldService.findAllByTableId(table.getId());
		List<FieldDTO> fieldDTOList = new ArrayList<>();
		for(Field field : fieldList){
			fieldDTOList.add(new FieldDTO(field, FieldTypeFlyweightFactory.getInstance().getFlyweight(field.getType())));
		}
		if(user != null){
            AnswerRecord answerRecord = answerRecordService.findByTableIdAndUserId(table.getId(), user.getId());
            //如果已经填写过该表
            if(answerRecord != null){
                List<Answer> answerList = answerService.findAllByRecordId(answerRecord.getId());
                List<String> myAnswerContentList = answerList.stream()
                        .map(Answer::getContent)
                        .collect(Collectors.toList());
                model.addAttribute("answerList", myAnswerContentList);
            }
        }
        model.addAttribute("table", table);
        model.addAttribute("fieldList", fieldDTOList);
		return "collect/inputCollect";
	}

    /**
     * @methodName: commitTable
     *          提交填写的收集表
     *          出现非已捕获的异常则会发生事务回滚
     *          若收集表需要上传多个文件，磁盘IO较多，事务较长，优先优化该接口
     * @return: String 返回Json
     */
    @PostMapping("/share/commit.action")
    @Transactional(rollbackFor = Exception.class)
	public String commitTable(@RequestParam(name = "file", required = false)MultipartFile[] files, Model model){
        HttpServletRequest request = WebUtil.getRequest();
        boolean isReFill = Boolean.valueOf(request.getParameter("isReFill"));
        int tid = Integer.parseInt(String.valueOf(request.getParameter("table_id")));
        Table table = tableService.findById(tid);
        User user = (User) request.getSession().getAttribute(SessionKey.USER);
        if(user == null){
            //到登录页面*************************
            return "redirect:/login";
        }
        //创建一条 AnswerRecord
        AnswerRecord record = new AnswerRecord();
        //是否曾经填写过该表
        if(!isReFill){
            record.setTableId(tid);
            record.setUserRealName(user.getRealname());
            record.setUserId(user.getId());
           //允许未登录填写未登录
            //     record.setUserRealName(request.getParameter("user-real-name"));
        } else{
            BeanUtils.copyProperties(answerRecordService.findByTableIdAndUserId(table.getId(), user.getId()), record);
        }
        record.setIp(IPUtil.ipv4StrToInt(IPUtil.getIpAddr(request)));
        record.setUpdate_time(new Date());
        boolean hasSetDeviceSystem = false;
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            record.setDeviceSystem(userAgent.getOperatingSystem().getName());
            hasSetDeviceSystem = true;
            record.setBrowser(userAgent.getBrowser().getName());
        }catch (Exception e){
            if(!hasSetDeviceSystem){
                record.setDeviceSystem("unknown");
            }
            record.setBrowser("unknown");
        }
        record = answerRecordService.save(record);
        int recordId = record.getId();
        //找出该收集表的全部字段
        List<Field> fieldList = fieldService.findAllByTableId(table.getId());
        //fields.sort(Comparator.comparing(Field::getNum));
        // TODO BUG 目前无法部分更新。若第二次填写没有提交文件，会导致错误，deleteAllByRecordId 删除原来所有的记录且无法回滚
        /**
         * BUG 原因：当前是删除原有的，然后添加新的。应该更新原有的 answer
         * 解决方案：采用更新answer替换删除再添加。
         *      成功执行完该事务后发送消息，异步删除旧文件。
         *          或
         *      定时任务：每天晚上删除数据库中不存在文件。
         */
        if(isReFill){
            answerService.deleteAllByRecordId(record.getId());
        }
        //文件数组下标
        int fileIndex = 0;
        List<Answer> answersList = new ArrayList<>(fieldList.size());
        for (Field field: fieldList) {
            Answer answer = new Answer();
            answer.setFieldId(field.getId());
            answer.setType(field.getType());
            answer.setAnswerRecordId(recordId);
            if(!"文件".equals(field.getType()))
                answer.setContent(request.getParameter("item-" + field.getNum()));
            else{
                MultipartFile file = files[fileIndex];
                //fileName，saveFile 表示上传文件保存路径
                String fileName = FilePath.getFieldFilesUploadPath(table.getId(), field);
                File saveFile = new File(fileName);
                if(!saveFile.exists() && !saveFile.mkdirs()) {
                    throw new RuntimeException(saveFile + " mkdirs failed!");
                }
                try {
                    String resourceFileName = file.getResource().getFilename();
                    //fileName，saveFile 表示上传文件保存路径+名称
                    if(StringUtils.isBlank(resourceFileName)){
                        throw new RuntimeException("resourceFileName is blank(null)");
                    }
                    fileName += UUID.randomUUID() + (resourceFileName.indexOf(".") > 0 ? resourceFileName.substring(resourceFileName.lastIndexOf(".")) : "");
                    saveFile = new File(fileName);

                    file.getOriginalFilename();
                    file.transferTo(saveFile);
                    answer.setContent(saveFile.getAbsolutePath());
                    fileIndex++;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("file transport exception!");
                }
            }
            answersList.add(answer);
        }
        //检查是否保存成功
        if(answerService.save(answersList) != null){
            if(!isReFill){
                tableService.increaseFilledNum(table.getId());
            }
            //向表主人发送消息，有人填写
            messageService.sendMessage(MessageTypeEnum.TABLE_FILLED, table);
            //返回前端
            model.addAttribute("resultMessage","感谢您的参与！");
            return "/displayMessage";
        }
        throw new RuntimeException("answers save to DB fail!");
	}

	//分享前的页面
	@GetMapping("/share/{tableId}")
    public String sharePage(@PathVariable("tableId") Integer tableId, Model model){
        Table table = new Table();
        // spring 包中的该方法是讲前者赋值给后者
        BeanUtils.copyProperties(tableService.findById(tableId), table);
        //如果是第一次点击发布
        if(TableStatusEnum.EDIT.equal(table.getState())){
            Date nowDate = new Date();
            if(nowDate.getTime() < table.getEndTime().getTime())
                table.setState(TableStatusEnum.COLLECTING.getValue());
            else {
                table.setState(TableStatusEnum.END.getValue());
            }
            table.setPublishTime(nowDate);
            table.setUpdateTime(nowDate);
            tableService.save(table);
        }
        table = TableStatusEnum.processTableState(table);
        model.addAttribute("shareURL", getShareCollectURL(tableId));
        model.addAttribute("table", table);
        return "collect/share";
    }

	//删除表
    @ResponseBody
    @PostMapping("/delete.action")
    public String deleteTable(HttpServletRequest request, HttpSession session){
        Integer tableId = Integer.parseInt(request.getParameter("t"));
        Integer operateUid = ((User)session.getAttribute(SessionKey.USER)).getId();
        if(tableService.deleteById(operateUid, tableId)){
            return JsonUtil.toJson(ResponseWrapper.success("删除成功!"));
        }else{
            return JsonUtil.toJson(ResponseWrapper.fail("删除失败!"));
        }
    }

	//暂停收集 id 为 t 的 收集表
    @ResponseBody
    @PostMapping("/stop.action")
    public String stopCollect(HttpServletRequest request, HttpSession session){
        Integer tableId = Integer.parseInt(request.getParameter("t"));
        Integer operateUid = ((User)session.getAttribute(SessionKey.USER)).getId();
        if(tableService.stopById(operateUid, tableId)){
            return JsonUtil.toJson(ResponseWrapper.success("已停止"));
        }else{
            return JsonUtil.toJson(ResponseWrapper.fail(""));
        }
    }

    /**
     * 整个收集表下载
     * @return null:校验通过，进入下载，其他：下载失败，返回提示信息
     */
    @ResponseBody
    @GetMapping("/export")
    public String export(){
        return tableService.export();
    }

    //上传excel制作收集表页面
    @GetMapping("/new/byUpload")
    public String newByUploadPage(){
        return "collect/upLoadExcel";
    }


    //上传excel制作收集表页面，若上传并解析成功，则生成表，保存至数据库
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/new/byUpload.action")
    public String newByUpload(MultipartFile file){
        HttpServletRequest request = WebUtil.getRequest();
        StrategyParam fileAndStrategyDTO = new StrategyParam(request);
        String fileName;
        Table table = new Table();
        List<Field> fieldList = new ArrayList<>();
        ExcelReadStrategy strategy = new ExcelReadStrategy(fileAndStrategyDTO);
        try {
            String uploadFileName = file.getOriginalFilename();
            table.setName(uploadFileName ==null ? "" : uploadFileName.substring(0, uploadFileName.lastIndexOf(".")));
            fileName = FileTransportUtil.uploadFile(file);
            ExcelUtilAdapter.read(fileName, table, fieldList, strategy);

            table.setOwner(((User) WebUtil.getRequest().getSession().getAttribute(SessionKey.USER)).getId());
            Date nowDate = new Date();
            table.setStartTime(nowDate);
            table.setEndTime(new Date(nowDate.getTime() + DEFAULT_END_TIME));
            table.setCreateTime(nowDate);
            table.setUpdateTime(nowDate);
            table.setState(TableStatusEnum.EDIT.getValue());
            table.setMaxFillNum(100);
            table.setFilledNum(0);
            table.setVisibility(true);
            table = tableService.save(table);

            int fieldNum = 1;
            if(fieldList.size() > 0){
                for (Field field : fieldList){
                    field.setTableId(table.getId());
                    field.setNum(fieldNum ++);
                    field.setType("普通");
                    field.setMaxLength(50);
                    field.setRequired(false);
                    field.setVisibility(true);

                }
            }
            fieldService.save(fieldList);
            return "redirect:/collect/edit?t=" + table.getId();
        }catch (Exception e){
            e.printStackTrace();
            throw new ResourceNotFoundException();
        }
    }

    /**
     上传的文件形式为 dir/upload/tableId_tableName/fieldId_fieldName/userId_userName_FieldName.xxx
     * 下载表收集的所有文件：          zip(dir/upload/tableId_tableName)
     * 下载表的特定字段下的的所有文件： zip(dir/upload/tableId_tableName/fieldId_fieldName)
     * 下载特定文件                   dir/upload/tableId_tableName/fieldId_fieldName/userId_userName_FieldName.xxx
     * zip 形式下载一个表的所有文件
     *
     * 请求示例     /download?t=1&fieldId=2 或者 /download?t=1& 或者 /download?t=1&?answerId=11
     */
    @ResponseBody
    @GetMapping("/download")
    public String download(){
        HttpServletRequest request = WebUtil.getRequest();
        HttpSession session = WebUtil.getRequest().getSession();
        User user = (User)session.getAttribute(SessionKey.USER);
        if(user != null){
            int tid = Integer.parseInt(request.getParameter("t"));
            Table table = tableService.findById(tid);
            //判断是否存在且是表的拥有者
            if(table != null && user.getId().equals(table.getOwner())){
                String answerIdStr = request.getParameter("answerId");
                //判断是否是单一文件
                if(StringUtils.isNotEmpty(answerIdStr)){
                    //下载 answer.id = answerIdStr 的文件
                    try{
                        int answer_id = Integer.parseInt(answerIdStr);
                        Answer answer = answerService.findById(answer_id);
                        if(!"文件".equals(answer.getType())){
                            return JsonUtil.toJson(ResponseWrapper.fail("要下载的文件是非文件字段"));
                        }
                        File file = new File(answer.getContent());
                        FileTransportUtil.downloadFile(file);
                    }catch(Exception e){
                        log.info(e.toString());
                        e.printStackTrace();
                    }
                }else {
                    // 若url参数 fieldId 非空则下载 fieldId 字段所有文件，否则压缩整个表的文件，并下载
                    String fieldIdStr = request.getParameter("fieldId");
                    // 希望被压缩的文件或目录
                    String wantedFilesPath;
                    // 压缩后的文件
                    String zipPath;
                    if(StringUtils.isNotEmpty(fieldIdStr)){
                        //下载某个字段的所有文件
                        int fieldId = Integer.parseInt(fieldIdStr);
                        Field field = fieldService.findById(fieldId);
                        wantedFilesPath = FilePath.getFieldFilesUploadPath(table.getId(), field);
                        zipPath = FilePath.getTableProcessedPath(table.getId());
                    }else{
                        //下载整个表的所有文件
                        wantedFilesPath = FilePath.getTableFilesUploadPath(table.getId());
                        zipPath = FilePath.getTableProcessedPath(table.getId());
                    }
                    //下载目标文件
                    try {
                        //这里是路径
                        File zipFile = new File(zipPath);
                        //创建文件
                        if(!zipFile.getParentFile().exists()) {
                            //如果目标文件所在的目录不存在，则创建父目录
                            if(!zipFile.getParentFile().mkdirs()) {
                                throw new RuntimeException("创建目录失败！");
                            }
                        }
                        if (!zipFile.createNewFile()) {
                            throw new RuntimeException("创建压缩文件失败！");
                        }
                        //写到磁盘
                        ZipUtil.zip(wantedFilesPath, new FileOutputStream(zipFile));
                        //从磁盘读到网络（发给浏览器）
                        FileTransportUtil.downloadFile(zipFile, table.getName() + ".zip");
                        if(!zipFile.delete()){
                            log.info(zipFile.getAbsolutePath() + " 未删除！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //传输结束返回
                    return null;
                }
                return JsonUtil.toJson(ResponseWrapper.fail("请求参数错误"));
            }
            return JsonUtil.toJson(ResponseWrapper.fail("权限不足"));
        }
        return JsonUtil.toJson(ResponseWrapper.fail("未登录"));
    }

    /**
     *  返回收集表的二维码
     * @param tableId tableId
     */
    @GetMapping("/QrCode/{tableId}")
    public String generateQrcCode(@PathVariable("tableId") Integer tableId){
        try {
            QrCodeUtilAdapter.generate(tableId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + WebMvcConfig.VIRTUAL_DIR +
                "processed/" + tableId + "/" + FilePath.FILE_NAME_TABLE_QrCODE_JPEG;
    }

    /**
     *  表详情页面
     */
    @GetMapping("/my/detail")
    public String tableDataPage(Model model){
        HttpServletRequest request = WebUtil.getRequest();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SessionKey.USER);
        if(user != null){
            int tid = Integer.parseInt(request.getParameter("t"));
            Table table = tableService.findById(tid);
            if(table != null && user.getId().equals(table.getOwner())){
                List<Field> fieldList = fieldService.findAllByTableId(table.getId());
                table = TableStatusEnum.processTableState(table);
                model.addAttribute("table", table);
                model.addAttribute("fieldList", fieldList);
                return "collect/tableDetail";
            }
        }
        throw new ResourceNotFoundException();
    }

    /**
     *  表数据页面，根据可见性展示
     * @param tableId tableId
     */
    @GetMapping("/data/{tableId}")
    public String tableDataPage(@PathVariable("tableId") Integer tableId, Model model){
        Table table = tableService.findById(tableId);
        if(table == null){
            throw new ResourceNotFoundException();
        }
        HttpSession session = WebUtil.getRequest().getSession();
        User user = (User)session.getAttribute(SessionKey.USER);
        //判断是否是表主人
        boolean isOwner = false;
        if(user.getId().equals(table.getOwner()))
            isOwner = true;
        //可见权限判断
        if(!isOwner && !table.getVisibility()){
            model.addAttribute("resultMessage", "只有表主人可以查看这张表哦~");
            return "displayMessage";
        }
        try{
            tableService.processTableData(tableId, isOwner, model);
        }catch (RuntimeException e){
            e.printStackTrace();
            model.addAttribute("resultMessage", "只有表主人可以查看这张表哦~");
            return "displayMessage";
        }
        table = TableStatusEnum.processTableState(table);
        model.addAttribute("table", table);
        return "collect/tableData";
    }

    //根据收集表 id 返回其填写地址 URL
    public static String getShareCollectURL(int tableId){
        String serverURL = WebUtil.getServerURL();
        return  serverURL + "/collect/share/start?t=" + tableId;
    }

    @RequestMapping("/like/{tableId}")
    public String like(@PathVariable("tableId") Integer tableId){
        tableService.addLikeTable(tableId);
        return "redirect:/index";
    }
}
