import { http } from './http';
import { CardCompanyGetResponse } from './cardCompany';

export default {
  login: (userSimplePassword: string) =>
    http.post<CommonResponse>('api/user/login', { userSimplePassword }),
  phone: (phoneNumber: string) =>
    http.post<PhoneResponse>('api/user/phone', { phoneNumber: phoneNumber }),
  signup: (userData: SignupRequest) => http.token<CommonResponse>('api/user/signup', userData),
  password: (userSimplePassword: string) =>
    http.post<CommonResponse>('api/user/password', { userSimplePassword }),
  cardCompany: () => http.get<CardCompanyGetResponse>('api/user/card-company'),
};

interface CommonResponse {
  statusCode: number;
  messages: string;
  developerMessage: string;
  timestamp: string;
}

interface PhoneResponse {
  statusCode: number;
  messages: string;
  developerMessage: string;
  timestamp: string;
  data: string;
}

interface SignupRequest {
  userName: string;
  userSocialNumber: string;
  userPhoneNumber: string;
  userGenderAndGenerationCode: string;
  userSimplePassword: string;
}
