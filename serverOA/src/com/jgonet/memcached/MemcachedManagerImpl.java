package com.jgonet.memcached;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.jgonet.context.WebApplicationContext;

public class MemcachedManagerImpl implements IMemcachedManager {
	private static final Logger logger = Logger.getLogger(MemcachedManagerImpl.class);
	public static String DEFAULT_MEMCACHED = "default";

	public static String PRECISION_MEMCACHED = "precision";
	
	public static String COMMON_MEMCACHED = "common";
	
	public static String LOCAL_MEMCACHED = "local";
	
	public static String ITMARKET_MEMCACHED = "itmarket";

	private String mServer;

	private MemCachedClient memCachedClient;// = new MemCachedClient();

	private String servers;

	private String weights;

	private String initConn;

	private String minConn;

	private String maxConn;

	private String maxIdle;

	private String maintSleep;

	private String nagle;

	private String socketTo;

	private String socketConnectTo;

	private String compressEnable;

	private String compressThreshold;

	// private static MemcachedManagerImpl memcachedManagerImpl = new
	// MemcachedManagerImpl();

	// public static MemcachedManagerImpl getInstance() {
	// return memcachedManagerImpl;
	// }

	public MemcachedManagerImpl(String memcached) {
		//

		if ( null == memcached ) {
			this.mServer = DEFAULT_MEMCACHED;
		}else if(memcached.equals(COMMON_MEMCACHED)){
			this.mServer = memcached;
		}else if(memcached.equals(LOCAL_MEMCACHED)){
			this.mServer = memcached;
		}else if(memcached.equals(ITMARKET_MEMCACHED)){
			this.mServer = memcached;
		}else {
			this.mServer = PRECISION_MEMCACHED;
		}
		memCachedClient = new MemCachedClient(this.mServer);
		String key = (memcached == null || DEFAULT_MEMCACHED.equals(memcached)) ? "" : (memcached + ".");
		this.servers = WebApplicationContext.getInstance().getConfigProperty(key + "memcached.servers");

		this.weights = WebApplicationContext.getInstance().getConfigProperty(key+"memcached.weights");
		this.initConn = WebApplicationContext.getInstance().getConfigProperty("memcached.initConn");
		this.minConn = WebApplicationContext.getInstance().getConfigProperty("memcached.minConn");
		this.maxConn = WebApplicationContext.getInstance().getConfigProperty("memcached.maxConn");
		this.maxIdle = WebApplicationContext.getInstance().getConfigProperty("memcached.maxIdle");
		this.maintSleep = WebApplicationContext.getInstance().getConfigProperty("memcached.maintSleep");
		this.nagle = WebApplicationContext.getInstance().getConfigProperty("memcached.nagle");
		this.socketTo = WebApplicationContext.getInstance().getConfigProperty("memcached.socketTo");
		this.socketConnectTo = WebApplicationContext.getInstance().getConfigProperty("memcached.socketConnectTo");
		this.compressEnable = WebApplicationContext.getInstance().getConfigProperty("memcached.compressEnable");
		this.compressThreshold = WebApplicationContext.getInstance().getConfigProperty("memcached.compressThreshold");

		String swt = WebApplicationContext.getInstance().getConfigProperty("memcached.ON_OFF");
		if ( "ON".equalsIgnoreCase(swt) ) {
			initialize();
		}
	}

	public String getCompressEnable() {
		return compressEnable;
	}

	public void setCompressEnable(String compressEnable) {
		this.compressEnable = compressEnable;
	}

	public String getCompressThreshold() {
		return compressThreshold;
	}

	public void setCompressThreshold(String compressThreshold) {
		this.compressThreshold = compressThreshold;
	}

	public String getInitConn() {
		return initConn;
	}

	public void setInitConn(String initConn) {
		this.initConn = initConn;
	}

	public String getMaintSleep() {
		return maintSleep;
	}

	public void setMaintSleep(String maintSleep) {
		this.maintSleep = maintSleep;
	}

	public String getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(String maxConn) {
		this.maxConn = maxConn;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public MemCachedClient getMemCachedClient() {
		return memCachedClient;
	}

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	public String getNagle() {
		return nagle;
	}

	public void setNagle(String nagle) {
		this.nagle = nagle;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getSocketConnectTo() {
		return socketConnectTo;
	}

	public void setSocketConnectTo(String socketConnectTo) {
		this.socketConnectTo = socketConnectTo;
	}

	public String getSocketTo() {
		return socketTo;
	}

	public void setSocketTo(String socketTo) {
		this.socketTo = socketTo;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public void initialize() {
		logger.info("初始化Memcached客户端...");
		// 开始验证...
		// 缓存服务器和其权重是否配置验证
		if ( servers == null || servers.trim().equals("") ) {
			logger.error("服务器列表配置不能为空......缓存建立失败！");
		}
		if ( weights == null || weights.trim().equals("") ) {
			logger.error("服务器的权重配置不能为空......缓存建立失败！");
		}

		// 服务器列表和其权重
		String[] servers = this.servers.split(",");
		String[] strWeights = weights.split(",");
		Integer[] weights = new Integer[strWeights.length];
		for (int i = 0; i < strWeights.length; i++) {
			weights[i] = Integer.parseInt(strWeights[i]);
		}

		// 缓存服务器和其权重配置个数是否相等验证
		if ( servers.length != weights.length ) {
			logger.error("服务器和其权重配置个数不相等......缓存建立失败！");
		}

		// 初始连接数、最小和最大连接数以及最大处理时间验证
		if ( initConn != null && !Pattern.compile("[0-9]*").matcher(initConn).matches() ) {
			logger.error("初始连接数配置不能为字符......加载失败！");
		}
		if ( minConn != null && !Pattern.compile("[0-9]*").matcher(minConn).matches() ) {
			logger.error("最小连接数配置不能为字符......加载失败！");
		}
		if ( maxConn != null && !Pattern.compile("[0-9]*").matcher(maxConn).matches() ) {
			logger.error("最大连接数配置不能为字符......加载失败！");
		}
		if ( maxIdle != null && !Pattern.compile("[0-9]*").matcher(maxIdle).matches() ) {
			logger.error("最大处理时间配置不能为字符......加载失败！");
		}

		// 主线程的睡眠时间验证
		if ( maintSleep != null && !Pattern.compile("[0-9]*").matcher(maintSleep).matches() ) {
			logger.error("主线程睡眠时间配置不能为字符......加载失败！");
		}

		// TCP的参数，连接超时验证
		if ( nagle != null && !nagle.trim().equals("") && !nagle.trim().equals("false") && !nagle.trim().equals("true") ) {
			logger.error("TCP参数nagle配置只能为空值或者true或者false......加载失败！");
		}
		if ( socketTo != null && !Pattern.compile("[0-9]*").matcher(socketTo).matches() ) {
			logger.error("TCP参数socketTo配置不能为字符......加载失败！");
		}
		if ( socketConnectTo != null && !Pattern.compile("[0-9]*").matcher(socketConnectTo).matches() ) {
			logger.error("TCP参数socketConnectTo配置不能为字符......加载失败！");
		}

		// 压缩设置验证
		if ( compressEnable != null && !compressEnable.trim().equals("") && !compressEnable.trim().equals("false") && !compressEnable.trim().equals("true") ) {
			logger.error("是否压缩配置只能为空值或者true或者false......加载失败！");
		}
		if ( compressThreshold != null && !Pattern.compile("[0-9]*").matcher(compressThreshold).matches() ) {
			logger.error("指定压缩大小(单位:K)配置不能为字符......加载失败！");
		}
		// 验证结束...

		// 获取socke连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance(this.mServer);

		// 设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);

		// 设置初始连接数、最小和最大连接数以及最大处理时间
		if ( initConn != null && !initConn.trim().equals("") ) {
			pool.setInitConn(Integer.parseInt(initConn));
		}
		if ( minConn != null && !minConn.trim().equals("") ) {
			pool.setMinConn(Integer.parseInt(minConn));
		}
		if ( maxConn != null && !maxConn.trim().equals("") ) {
			pool.setMaxConn(Integer.parseInt(maxConn));
		}
		if ( maxIdle != null && !maxIdle.trim().equals("") ) {
			pool.setMaxIdle(Integer.parseInt(maxIdle));
		}

		// 设置主线程的睡眠时间
		if ( maintSleep != null && !maintSleep.trim().equals("") ) {
			pool.setMaintSleep(Long.parseLong(maintSleep));
		}

		// 设置TCP的参数，连接超时等
		if ( nagle != null && !nagle.trim().equals("") && (nagle.trim().equals("false") || nagle.trim().equals("true")) ) {
			pool.setNagle(Boolean.parseBoolean(nagle));
		}
		if ( socketTo != null && !socketTo.trim().equals("") ) {
			pool.setSocketTO(Integer.parseInt(socketTo));
		}
		if ( socketConnectTo != null && !socketConnectTo.trim().equals("") ) {
			pool.setSocketConnectTO(Integer.parseInt(socketConnectTo));
		}

		// 初始化连接池
		pool.initialize();

		// 压缩设置，超过指定大小（单位为K）的数据都会被压缩
		if ( compressEnable != null && !compressEnable.trim().equals("") && (compressEnable.trim().equals("false") || compressEnable.trim().equals("true")) ) {
			// memCachedClient.setCompressEnable(Boolean.parseBoolean(compressEnable));
		}
		if ( compressThreshold != null && !compressThreshold.trim().equals("") ) {
			// memCachedClient.setCompressThreshold(Long.parseLong(compressThreshold));
		}

		logger.info("Memcached客户端初始化成功!");
	}

	public boolean add(String key, Object value, long expireInMilliSeconds) {
		Date d = new Date(expireInMilliSeconds);
		return this.memCachedClient.add(key, value, d);
	}

	public boolean add(String key, Object value) {
		return this.memCachedClient.add(key, value);
	}
	public boolean add(String key, String value) {
		return this.memCachedClient.add(key, value);
	}

	public boolean delete(String key) {
		return this.memCachedClient.delete(key);
	}

	public boolean replace(String key, Object value) {
		return this.memCachedClient.replace(key, value);
	}

	public boolean replace(String key, Object value, long expireInMilliSeconds) {
		Date d = new Date(expireInMilliSeconds);
		return this.memCachedClient.replace(key, value, d);
	}

	public Object get(String key) {
		return this.memCachedClient.get(key);
	}

	public String getString(String key) {
		Object obj=this.memCachedClient.get(key);
		return obj==null?null:obj.toString();
	}
	public String getMinConn() {
		return minConn;
	}

	public void setMinConn(String minConn) {
		this.minConn = minConn;
	}

	@Override
	public void expire(String key, long expireInMilliSeconds) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public long incr(String key, int seconds) {
		return -1;
	}
}
