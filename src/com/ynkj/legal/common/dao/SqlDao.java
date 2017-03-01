package com.ynkj.legal.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 数据库访问接口
 * <p>要支持各种数据库，只需要实现该接口即可。</p>
 *
 * @author hy
 * @version 1.0
 */
public interface SqlDao {

    /**
     * 插入记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 插入对象
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int create(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 更新记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 更新对象
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int update(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 删除记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 删除对象
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int delete(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 查询单个数据记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询参数
     * @return Object 单个数据对象
     * @throws DataAccessException 数据库访问异常
     */
    public Object view(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID   sqlmap文件sql语句对应的id
     * @param mapping 查询条件
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Map mapping) throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID   sqlmap文件sql语句对应的id
     * @param mapping 查询条件
     * @param escape 是否对转义字符进行转义
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Map mapping, boolean escape) throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询参数
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 查询某一页数据
     * @param sqlID sqlmap文件sql语句对应的id
     * @param object 查询条件对象
     * @param skipResults 从第几条数据开始取数据
     * @param maxResults 最多取多少条数据
     * @return 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List pageList(String sqlID, Map object, int skipResults, int maxResults)
            throws DataAccessException;

    /**
     * 查询某一页数据
     * @param sqlID sqlmap文件sql语句对应的id
     * @param object 查询SQL语句
     * @param skipResults 从第几条数据开始取数据
     * @param maxResults 最多取多少条数据
     * @return 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List pageList(String sqlID, String object, int skipResults, int maxResults)
            throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sqlID     sqlmap文件sql语句对应的id
     * @param params    查询条件
     * @param useConfig 是否使用配置的count sql来查询记录数
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public int count(String sqlID, Map params, boolean useConfig)
            throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public int count(String sqlID, Map params) throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sql  统计记录数的SQL语句
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int count(String sql) throws DataAccessException;

    /**
     * 执行SQL语句
     * @param sql SQL语句
     * @throws DataAccessException
     */
    public void execute(String sql) throws DataAccessException;

    /**
     * 执行一个查询SQL语句
     * @param sql SQL语句
     * @return MapBean的List
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
    public List select(String sql) throws DataAccessException;
    
    /**
     * 批量更新
     * @param statementName
     * @param list 要更新的list
     */
    public void batchUpdate(String statementName,List<?> list);
    
    
    /**
     * 批量删除
     * @param statementName
     * @param list
     */
    public void batchDelete(String statementName,List<?> list);
    
    
    /***
     * 数组转换分页
     * @param pageNum
     * @param pageCount
     * @param li
     * @return
     */
    public Object[] getObject(int pageNum, int pageCount, List li);
}