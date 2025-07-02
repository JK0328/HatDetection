import request from '/@/utils/request';

export function useDriverDetectionApi() {
	return {
		// 各年龄段不佩戴头盔分布
		getNoHelmetByAge: (params?: object) => request({ url: '/api/driverdetection/noHelmetByAge', method: 'get', params }),
		// 男女性别不戴头盔情况
		getNoHelmetByGender: (params?: object) => request({ url: '/api/driverdetection/noHelmetByGender', method: 'get', params }),
		// 按天统计不戴头盔数目
		getNoHelmetByDay: (params?: object) => request({ url: '/api/driverdetection/noHelmetByDay', method: 'get', params }),
		// 不同地区不戴头盔数目
		getNoHelmetByRegion: (params?: object) => request({ url: '/api/driverdetection/noHelmetByRegion', method: 'get', params }),
	};
} 