import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct CalendarView: View {
    @StateViewModel var weekViewModel = WeekViewModel()
    var today: Models.Day = Week().getTodayDate()
    @Environment(\.colorScheme) var colorScheme
    @State private var dragging = false
    @State private var translation: CGFloat = 0.0
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                HStack(spacing: 0) {
                    ForEach(weekViewModel.selectedWeek.week, id: \.id) { day in
                        VStack {
                            Text(day.day.prefix(3))
                                .font(.footnote)
                                .foregroundColor(colorScheme == .dark ? .white : .black)
                            Button(action: {
                                weekViewModel.setSelectedDay(day: day)
                            }) {
                                TextView(day: day, today: today, colorScheme: colorScheme)
                            }
                            Text(day.hours)
                                .font(.footnote)
                                .foregroundColor(day.number == today.number && day.month == today.month ? .orange : colorScheme == .light ? .black : .white)
                        }
                        .frame(width: geometry.size.width / 7)
                    }
                }
                .offset(x: dragging ? translation : 0)
                .animation(.easeInOut)
                .gesture(
                    DragGesture()
                        .onChanged { value in
                            translation = value.translation.width
                            dragging = true
                        }
                        .onEnded { value in
                            dragging = false
                            if value.translation.width < -geometry.size.width / 2 {
                                weekViewModel.setFollowingWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
                            } else if value.translation.width > geometry.size.width / 2 {
                                weekViewModel.setPreviousWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
                            }
                            withAnimation {
                                translation = 0
                            }
                        }
                )
            }.fixedSize()
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
        .cornerRadius(10)
        .frame(height: 100)
        .onAppear(){
            weekViewModel.setCurrentWeek(day: weekViewModel.selectedWeek.week[0].number, monthName: weekViewModel.selectedWeek.week[0].month)
        }
        
        Spacer()
    }
}

struct TextView: View {
    @StateViewModel var weekViewModel = WeekViewModel()
    var day: Models.Day
    var today: Models.Day
    var colorScheme: ColorScheme
    
    var body: some View {
        Text(day.number)
            .font(.footnote)
            .fontWeight(.medium)
            .foregroundColor(colorScheme == .light ? weekViewModel.selectedDay.number == day.number && weekViewModel.selectedDay.month == day.month ? .white : day.number == today.number && day.month == today.month ? .orange : weekViewModel.selectedDay.day == day.day && weekViewModel.selectedDay.month == day.month ? .white : .black : weekViewModel.selectedDay.number == day.number && weekViewModel.selectedDay.month == day.month ? .black : day.number == today.number && day.month == today.month ? .orange : weekViewModel.selectedDay.day == day.day && weekViewModel.selectedDay.month == day.month ? .black : .white)
            .frame(width: 30, height: 30)
            .background(colorScheme == .light ? weekViewModel.selectedDay.number == day.number && weekViewModel.selectedDay.month == day.month ? weekViewModel.selectedDay.number == today.number && weekViewModel.selectedDay.month == today.month ? Color.orange : Color.black : weekViewModel.selectedDay.day == day.day && weekViewModel.selectedDay.month == day.month ? Color.black : Color.clear : weekViewModel.selectedDay.number == day.number && weekViewModel.selectedDay.month == day.month ? weekViewModel.selectedDay.number == today.number && weekViewModel.selectedDay.month == today.month ? Color.orange : Color.white : weekViewModel.selectedDay.day == day.day && weekViewModel.selectedDay.month == day.month ? Color.white : Color.clear)
            .clipShape(Circle())
    }
}
