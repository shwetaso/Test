package com.provenir.apps.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.provenir.apps.cl.securitydata.GU;
import com.provenir.apps.cl.securitydata.GUAgentDTO;
import com.provenir.apps.cl.securitydata.ProvResourceDTO;
import com.provenir.apps.cl.securitydata.ResourceAgentDTO;
import com.provenir.apps.service.security.svc.dto.KeyValue;
import com.provenir.apps.service.security.svc.dto.KeyValueAllDTO;

public class RestServiceHelper {
    private static final Logger logger = Logger.getLogger(RestServiceHelper.class);

    public static KeyValueAllDTO convertListToDTO(List<String> list) {

        KeyValueAllDTO kvWrapper = new KeyValueAllDTO();
        List<KeyValue> keyValueList = new ArrayList<KeyValue>();
        for (String item : list) {
            KeyValue kv = new KeyValue();
            kv.setKey(item);
            keyValueList.add(kv);
        }

        kvWrapper.setKeyValueList(keyValueList);
        return kvWrapper;
    }

    public static List<ResourceAgentDTO> convertMapToList(
            ConcurrentMap<String, HashMap<String, GU>> securityAccessRights) {
        final List<ResourceAgentDTO> resourceAgentDtos = new ArrayList<ResourceAgentDTO>();
        final Set<String> resourceIds = securityAccessRights.keySet();

        for (String reourceId : resourceIds) {
            ResourceAgentDTO resourceAgentDto = new ResourceAgentDTO();
            resourceAgentDto.setResourceId(reourceId);
            HashMap<String, GU> agentDtos = securityAccessRights.get(reourceId);
            Set<String> agents = agentDtos.keySet();
            List<GUAgentDTO> guAgentDTOs = new ArrayList<GUAgentDTO>();
            for (String agent : agents) {
                GUAgentDTO guAgentDto = new GUAgentDTO();
                guAgentDto.setAgent(agent);
                guAgentDto.setGu(agentDtos.get(agent));
                guAgentDTOs.add(guAgentDto);
            }
            resourceAgentDto.setGuAgentDtos(guAgentDTOs);
            resourceAgentDtos.add(resourceAgentDto);
        }
        return resourceAgentDtos;
    }

    public static List<ProvResourceDTO> convertProvResMapToList(ConcurrentMap<String, String> provResourceToIdCache) {
        List<ProvResourceDTO> resourceAgentDtos = new ArrayList<ProvResourceDTO>();
        if (provResourceToIdCache != null && !provResourceToIdCache.isEmpty()) {
            Set<String> resourceIds = provResourceToIdCache.keySet();
            for (String resId : resourceIds) {
                ProvResourceDTO dto = new ProvResourceDTO();
                dto.setResourceId(resId);
                dto.setType(provResourceToIdCache.get(resId));
                resourceAgentDtos.add(dto);
            }
        }
        return resourceAgentDtos;
    }

    public static KeyValueAllDTO convertMapToDTO(Map<String, List<String>> map) {
        KeyValueAllDTO kvWrapper = new KeyValueAllDTO();
        List<KeyValue> keyValueList = new ArrayList<KeyValue>();
        for (String key : map.keySet()) {
            List<String> valueList = map.get(key);
            for (String value : valueList) {
                KeyValue keyValue = new KeyValue();
                keyValue.setKey(key);
                keyValue.setValue(value);
                keyValueList.add(keyValue);
            }

        }
        kvWrapper.setKeyValueList(keyValueList);
        return kvWrapper;
    }
}
