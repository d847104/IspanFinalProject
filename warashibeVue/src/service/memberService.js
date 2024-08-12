import axiosapi from '@/plugins/axios';

export const getMemberById = async (memberID) => {
    try {
        const response = await axiosapi.get(`/ajax/members/${memberID}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching member data:', error);
        throw error;
    }
};
