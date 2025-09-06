package org.example.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import groovy.util.logging.Slf4j;
import org.example.shortlink.admin.dao.entity.GroupDO;
import org.example.shortlink.admin.dao.mapper.GroupMapper;
import org.example.shortlink.admin.service.GroupService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
}
