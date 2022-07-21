import string
import pandas as pd
        

class FileAnalyzer:

    def analyze_file(self, path):
        original_file = pd.read_csv(path, sep="\n\n", names = ['line'], header=None, engine='python')

        original_file[['time', 'number_of_car']] = original_file['line'].str.split(' ', expand=True)
        original_file['number_of_car'] = pd.to_numeric(original_file['number_of_car'])
        original_file['time'] = pd.to_datetime(original_file['time'])
        original_file['date'] = original_file['time'].dt.date

        print("1. The number of cars seen in total:", original_file['number_of_car'].sum(), sep='\n')
        print()

        file_groupby_date = original_file.groupby(['date'])['number_of_car'].sum().reset_index(name ='total_number_of_car')
        print("2. Number of cars seen per day:", file_groupby_date.to_string(index=False), sep='\n')
        print()

        print('3. The top 3 half hour with most cars:', original_file.nlargest(3, 'number_of_car')['line'].to_string(index=False), sep='\n')
        print()

        file_deduplicated_sorted_by_time = original_file.sort_values(by=['time']).drop_duplicates('time', keep='last')  
        file_dict = file_deduplicated_sorted_by_time.to_dict('index')

        # least of consecutive n p? => dedup. sort
        start = 0
        if len(file_dict) < 3:
            least_car = None
        else:
            least_car = file_dict[0]['number_of_car']+  file_dict[1]['number_of_car'] +  file_dict[2]['number_of_car']
            for i in range(1, len(file_dict)-2):
                time_difference_minute =  (file_dict[i+2]['time'] - file_dict[i]['time']).total_seconds() / 60
                if time_difference_minute <= 60:
                    if file_dict[i]['number_of_car'] + file_dict[i+1]['number_of_car'] + file_dict[i+2]['number_of_car'] < least_car:
                        start = i
                        least_car = file_dict[i]['number_of_car'] + file_dict[i+1]['number_of_car'] + file_dict[i+2]['number_of_car']
            df_least = file_deduplicated_sorted_by_time[start:start+3]

        print('4. The 1.5 hour period with least cars:')
        if not least_car:
            print("No enough data.")
        else:
            print(df_least[['time', 'number_of_car']].to_string(index=False), sep='\n')
        print()
        
