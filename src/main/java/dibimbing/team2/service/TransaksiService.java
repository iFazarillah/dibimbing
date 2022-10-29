package dibimbing.team2.service;


import dibimbing.team2.dao.TransaksiRequest;

import java.util.Map;

public interface TransaksiService {

    public Map simpan(TransaksiRequest obj);

    public Map update(TransaksiRequest obj);

    public Map delete(Long obj);

    public Map cancel(Long obj);

    public Map getById(Long id);

}
