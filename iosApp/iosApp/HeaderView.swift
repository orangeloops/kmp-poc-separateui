import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct HeaderView: View {
    @StateViewModel var weekViewModel = WeekViewModel()
    var today: Models.Day = Week().getTodayDate()
    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        VStack(spacing: 0){
            HStack {
                Text("\(Week().getDateFormatted(day: weekViewModel.selectedDay.day, m: weekViewModel.selectedDay.month, dayOfMonth:  weekViewModel.selectedDay.number))")
                    .font(.footnote)
                    .fontWeight(.medium)
                    .fixedSize(horizontal: false, vertical: true)
                    .multilineTextAlignment(.center)
                    .padding(10)
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .fill(colorScheme == .dark ? Color.black : Color.white)
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color.gray, lineWidth: 1)
                            )
                    )
                    .padding(.leading, 20)
                
                Spacer()
                
                if !(weekViewModel.selectedDay.day == today.day && weekViewModel.selectedDay.month == today.month){
                    Button(action: {
                        weekViewModel.setCurrentWeek()
                        weekViewModel.setSelectedDay(day: Week().getTodayDate())
                    }) {
                        Image(systemName: "calendar")
                            .font(.body)
                            .foregroundColor(.gray)
                    }
                    .padding(.trailing, 20)
                }
                
                Button(action: {
                    weekViewModel.setPreviousWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
                    weekViewModel.setSelectedDay(day: Week().getPreviousWeekDate(day: weekViewModel.selectedDay.number, monthName: weekViewModel.selectedDay.month))
                }) {
                    Image(systemName: "chevron.left")
                        .font(.body)
                        .foregroundColor(.gray)
                }
                .padding(.trailing, 20)
                
                Button(action: {
                    weekViewModel.setFollowingWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
                    weekViewModel.setSelectedDay(day: Week().getNextWeekDate(day: weekViewModel.selectedDay.number, monthName: weekViewModel.selectedDay.month))
                }) {
                    Image(systemName: "chevron.right")
                        .font(.body)
                        .foregroundColor(.gray)
                }
                .padding(.trailing, 20)
            }
        }
        .frame(height: 50)
    }
}

